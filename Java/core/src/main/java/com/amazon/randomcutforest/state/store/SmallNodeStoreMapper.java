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

package com.amazon.randomcutforest.state.store;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

import com.amazon.randomcutforest.state.IStateMapper;
import com.amazon.randomcutforest.store.SmallNodeStore;

@Getter
@Setter
public class SmallNodeStoreMapper implements IStateMapper<SmallNodeStore, NodeStoreState> {
    /**
     * If true, then model data will be copied (i.e., the state class will not share
     * any data with the model). If false, some model data may be shared with the
     * state class. Copying is enabled by default.
     */
    private boolean copy = true;

    @Override
    public SmallNodeStore toModel(NodeStoreState state, long seed) {
        int capacity = state.getSmallLeftIndex().length;
        short[] leftIndex = Arrays.copyOf(state.getSmallLeftIndex(), capacity);
        short[] rightIndex = Arrays.copyOf(state.getSmallRightIndex(), capacity);
        short[] parentIndex = Arrays.copyOf(state.getSmallParentIndex(), capacity);
        short[] mass = Arrays.copyOf(state.getSmallMass(), capacity);
        int[] cutDimension = Arrays.copyOf(state.getCutDimension(), capacity);
        double[] cutValue = Arrays.copyOf(state.getCutValue(), capacity);

        short freeIndexPointer = (short) (state.getSmallFreeIndexes().length - 1);
        short[] freeIndexes = new short[capacity];
        System.arraycopy(state.getSmallFreeIndexes(), 0, freeIndexes, 0, freeIndexPointer + 1);

        return new SmallNodeStore(parentIndex, leftIndex, rightIndex, cutDimension, cutValue, mass, freeIndexes,
                freeIndexPointer);
    }

    @Override
    public NodeStoreState toState(SmallNodeStore model) {
        NodeStoreState state = new NodeStoreState();
        state.setSmallLeftIndex(Arrays.copyOf(model.leftIndex, model.leftIndex.length));
        state.setSmallRightIndex(Arrays.copyOf(model.rightIndex, model.rightIndex.length));
        state.setSmallParentIndex(Arrays.copyOf(model.parentIndex, model.parentIndex.length));
        state.setSmallMass(Arrays.copyOf(model.mass, model.mass.length));
        state.setCutDimension(Arrays.copyOf(model.cutDimension, model.cutDimension.length));
        state.setCutValue(Arrays.copyOf(model.cutValue, model.cutValue.length));
        state.setSmallFreeIndexes(Arrays.copyOf(model.getFreeIndexes(), model.getFreeIndexPointer() + 1));
        return state;
    }
}