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

import static com.amazon.randomcutforest.CommonUtils.checkNotNull;

import java.util.Arrays;

import com.amazon.randomcutforest.state.IStateMapper;
import com.amazon.randomcutforest.store.PointStoreFloat;

public class PointStoreFloatMapper implements IStateMapper<PointStoreFloat, PointStoreState> {

    @Override
    public PointStoreFloat toModel(PointStoreState state, long seed) {
        checkNotNull(state.getRefCount(), "refCount must not be null");
        checkNotNull(state.getFloatData(), "floatdata must not be null");

        int capacity = state.getRefCount().length;
        short[] refCount = Arrays.copyOf(state.getRefCount(), capacity);
        float[] store = Arrays.copyOf(state.getFloatData(), state.getFloatData().length);

        int freeIndexPointer = state.getFreeIndexes().length - 1;
        int[] freeIndexes = new int[capacity];
        System.arraycopy(state.getFreeIndexes(), 0, freeIndexes, 0, freeIndexPointer + 1);

        return new PointStoreFloat(store, refCount, freeIndexes, freeIndexPointer);
    }

    @Override
    public PointStoreState toState(PointStoreFloat model) {
        PointStoreState state = new PointStoreState();
        state.setFloatData(Arrays.copyOf(model.getStore(), model.getStore().length));
        state.setRefCount(Arrays.copyOf(model.getRefCount(), model.getRefCount().length));
        state.setFreeIndexes(Arrays.copyOf(model.getFreeIndexes(), model.getFreeIndexPointer() + 1));
        return state;
    }
}