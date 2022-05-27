/*
 * Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazon.randomcutforest.examples.parkservices;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.amazon.randomcutforest.config.ForestMode;
import com.amazon.randomcutforest.config.Precision;
import com.amazon.randomcutforest.examples.Example;
import com.amazon.randomcutforest.parkservices.AnomalyDescriptor;
import com.amazon.randomcutforest.parkservices.ThresholdedRandomCutForest;
import com.amazon.randomcutforest.parkservices.state.ThresholdedRandomCutForestMapper;
import com.amazon.randomcutforest.testutils.MultiDimDataWithKey;
import com.amazon.randomcutforest.testutils.ShingledMultiDimDataWithKeys;

public class ThresholdedMultiDimensionalExample implements Example {

    public static void main(String[] args) throws Exception {
        new ThresholdedMultiDimensionalExample().run();
    }

    @Override
    public String command() {
        return "Thresholded_Multi_Dim_example";
    }

    @Override
    public String description() {
        return "Thresholded Multi Dimensional Example";
    }

    @Override
    public void run() throws Exception {
        // Create and populate a random cut forest

        double[] trainingData = new double[]{25.0, 25.0, 25.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0,
                23.0, 23.0, 23.0, 23.0, 23.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 21.0, 21.0, 21.0,
                21.0, 21.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 19.0, 19.0, 19.0, 19.0, 19.0, 18.0,
                18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 17.0, 17.0, 17.0, 17.0, 17.0, 16.0, 16.0, 16.0, 16.0,
                16.0, 16.0, 16.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0,
                16.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 19.0,
                19.0, 19.0, 19.0, 19.0, 19.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 21.0, 21.0, 21.0,
                21.0, 21.0, 21.0, 21.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 23.0, 23.0, 23.0, 23.0,
                23.0, 23.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0, 23.0, 23.0, 23.0, 23.0, 23.0,
                23.0, 23.0, 23.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 21.0, 21.0, 21.0,
                21.0, 21.0, 21.0, 21.0, 21.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 19.0,
                19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0,
                18.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 16.0, 16.0,
                16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 15.0, 15.0,
                15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 14.0, 14.0,
                14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 13.0, 13.0,
                13.0, 13.0, 13.0, 13.0, 13.0, 13.0, 13.0, 14.0, 14.0, 14.0, 14.0, 15.0, 15.0, 15.0, 15.0,
                16.0, 16.0, 16.0, 16.0, 17.0, 17.0, 17.0, 18.0, 18.0, 18.0, 18.0, 19.0, 19.0, 19.0, 19.0,
                20.0, 20.0, 20.0, 20.0, 21.0, 21.0, 21.0, 22.0, 22.0, 22.0, 22.0, 23.0, 23.0, 23.0, 23.0,
                24.0, 24.0, 24.0, 24.0, 25.0, 25.0, 25.0, 26.0, 26.0, 26.0, 26.0, 27.0, 27.0, 27.0, 27.0,
                28.0, 28.0, 28.0, 28.0, 29.0, 29.0, 29.0, 29.0, 29.0, 29.0, 29.0, 29.0, 29.0, 28.0, 28.0,
                28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 27.0, 27.0,
                27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 26.0, 26.0,
                26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 25.0, 25.0,
                25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0, 25.0,
                26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0,
                26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0,
                27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 28.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 25.0, 25.0, 25.0, 25.0, 25.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0, 24.0, 23.0, 23.0, 23.0, 23.0, 23.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 21.0, 21.0, 21.0, 21.0, 21.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 19.0, 19.0, 19.0, 19.0, 19.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 22.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 21.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 13.0, 13.0, 13.0, 13.0, 13.0, 13.0, 13.0, 13.0, 12.0, 12.0, 12.0, 12.0, 12.0, 12.0, 12.0, 13.0, 13.0, 13.0, 14.0, 14.0, 14.0, 14.0, 14.0, 15.0, 15.0, 15.0, 16.0, 16.0, 16.0, 16.0, 16.0, 17.0, 17.0, 17.0, 18.0, 18.0, 18.0, 18.0, 18.0, 19.0, 19.0, 19.0, 20.0, 20.0, 20.0, 20.0, 20.0, 21.0, 21.0, 21.0, 22.0, 22.0, 22.0, 22.0, 22.0, 23.0, 23.0, 23.0, 24.0, 24.0, 24.0, 24.0, 24.0, 25.0, 25.0, 25.0, 26.0, 26.0, 26.0, 26.0, 26.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 27.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 26.0, 25.0, 25.0, 25.0, 25.0, 24.0, 24.0, 24.0, 24.0, 24.0, 23.0, 23.0, 23.0, 23.0, 23.0, 22.0, 22.0, 22.0, 22.0, 21.0, 21.0, 21.0, 21.0, 21.0, 20.0, 20.0, 20.0, 20.0, 20.0, 19.0, 19.0, 19.0, 19.0, 18.0, 18.0, 18.0, 18.0, 18.0, 17.0, 17.0, 17.0, 17.0, 16.0, 16.0, 16.0, 16.0, 16.0, 15.0, 15.0, 15.0, 15.0, 15.0, 14.0, 14.0, 14.0, 14.0, 13.0, 13.0, 13.0, 13.0, 13.0, 13.0, 13.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 14.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 15.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 16.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 17.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 18.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 19.0, 20.0, 20.0, 20.0, 20.0, 20.0};


        double[] postTrainingData = new double[]{13.0, 20.0, 26.0, 18.0};

        int shingleSize = 8;
        int numberOfTrees = 30;
        int sampleSize = 256;
        Precision precision = Precision.FLOAT_32;

        // change this to try different number of attributes,
        // this parameter is not expected to be larger than 5 for this example
        int baseDimensions = 1;
        long seed = -3095522926185205814L;

        int dimensions = baseDimensions * shingleSize;
        ThresholdedRandomCutForest forest = ThresholdedRandomCutForest.builder().compact(true).dimensions(dimensions)
                .randomSeed(seed).numberOfTrees(numberOfTrees).shingleSize(shingleSize).sampleSize(sampleSize)
                .precision(precision).parallelExecutionEnabled(false).outputAfter(32).internalShinglingEnabled(true).anomalyRate(0.005).initialAcceptFraction(0.125)
                .timeDecay(0.0001).boundingBoxCacheFraction(0).forestMode(ForestMode.STANDARD).build();
        for (double dataPoint : trainingData) {
            AnomalyDescriptor result = forest.process(new double[]{dataPoint}, 0L);
            System.out.println("result: " + result.getRCFScore());
        }

        //without this conversion between training data and post-training data failure doesn't occur
        // as in just processing the results in postTrainingData right after trainingData and no error is thrown
        ThresholdedRandomCutForestMapper mapper = new ThresholdedRandomCutForestMapper();
        ThresholdedRandomCutForest second = mapper.toModel(mapper.toState(forest));

        for (double dataPoint : postTrainingData) {
            AnomalyDescriptor result = second.process(new double[]{dataPoint}, 0L);
            System.out.println("result post conversion: " + result.getRCFScore());
        }
    }
//    // Create and populate a random cut forest
//
//    int shingleSize = 4;
//    int numberOfTrees = 50;
//    int sampleSize = 256;
//    Precision precision = Precision.FLOAT_32;
//    int dataSize = 4 * sampleSize;
//
//    // change this to try different number of attributes,
//    // this parameter is not expected to be larger than 5 for this example
//    int baseDimensions = 2;
//
//    int dimensions = baseDimensions * shingleSize;
//    ThresholdedRandomCutForest forest = ThresholdedRandomCutForest.builder().compact(true).dimensions(dimensions)
//            .randomSeed(0).numberOfTrees(numberOfTrees).shingleSize(shingleSize).sampleSize(sampleSize)
//            .precision(precision).anomalyRate(0.01).forestMode(ForestMode.STANDARD).build();
//
//    long seed = new Random().nextLong();
//        System.out.println("seed = "+seed);
//    // change the last argument seed for a different run
//    MultiDimDataWithKey dataWithKeys = ShingledMultiDimDataWithKeys.generateShingledDataWithKey(dataSize, 50,
//            shingleSize, baseDimensions, seed);
//    int keyCounter = 0;
//    int count = 0;
//        for(
//    double[] point :dataWithKeys.data)
//
//    {
//
//        AnomalyDescriptor result = forest.process(point, 0L);
//
//        if (keyCounter < dataWithKeys.changeIndices.length
//                && count + shingleSize - 1 == dataWithKeys.changeIndices[keyCounter]) {
//            System.out.println("timestamp " + (count + shingleSize - 1) + " CHANGE "
//                    + Arrays.toString(dataWithKeys.changes[keyCounter]));
//            ++keyCounter;
//        }
//
//        if (result.getAnomalyGrade() != 0) {
//            System.out.print("timestamp " + (count + shingleSize - 1) + " RESULT value ");
//            for (int i = (shingleSize - 1) * baseDimensions; i < shingleSize * baseDimensions; i++) {
//                System.out.print(result.getCurrentInput()[i] + ", ");
//            }
//            System.out.print("score " + result.getRCFScore() + ", grade " + result.getAnomalyGrade() + ", ");
//
//            if (result.isExpectedValuesPresent()) {
//                if (result.getRelativeIndex() != 0 && result.isStartOfAnomaly()) {
//                    System.out.print(-result.getRelativeIndex() + " steps ago, instead of ");
//                    for (int i = 0; i < baseDimensions; i++) {
//                        System.out.print(result.getPastValues()[i] + ", ");
//                    }
//                    System.out.print("expected ");
//                    for (int i = 0; i < baseDimensions; i++) {
//                        System.out.print(result.getExpectedValuesList()[0][i] + ", ");
//                        if (result.getPastValues()[i] != result.getExpectedValuesList()[0][i]) {
//                            System.out.print("( "
//                                    + (result.getPastValues()[i] - result.getExpectedValuesList()[0][i]) + " ) ");
//                        }
//                    }
//                } else {
//                    System.out.print("expected ");
//                    for (int i = 0; i < baseDimensions; i++) {
//                        System.out.print(result.getExpectedValuesList()[0][i] + ", ");
//                        if (result.getCurrentInput()[(shingleSize - 1) * baseDimensions
//                                + i] != result.getExpectedValuesList()[0][i]) {
//                            System.out
//                                    .print("( " + (result.getCurrentInput()[(shingleSize - 1) * baseDimensions + i]
//                                            - result.getExpectedValuesList()[0][i]) + " ) ");
//                        }
//                    }
//                }
//            }
//            System.out.println();
//        }
//        ++count;
//    }

}