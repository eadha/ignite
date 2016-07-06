/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.cache.database.tree.io;

import java.nio.ByteBuffer;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.internal.processors.cache.database.tree.util.PageHandler;

/**
 * Abstract IO routines for B+Tree leaf pages.
 */
public abstract class BPlusLeafIO<L> extends BPlusIO<L> {
    /**
     * @param type Page type.
     * @param ver Page format version.
     * @param itemSize Single item size on page.
     */
    protected BPlusLeafIO(int type, int ver, int itemSize) {
        super(type, ver, true, true, itemSize);
    }

    /** {@inheritDoc} */
    @Override public int getMaxCount(ByteBuffer buf) {
        return (buf.capacity() - ITEMS_OFF) / itemSize;
    }

    /** {@inheritDoc} */
    @Override public final void copyItems(ByteBuffer src, ByteBuffer dst, int srcIdx, int dstIdx, int cnt,
        boolean cpLeft) throws IgniteCheckedException {
        assert srcIdx != dstIdx || src != dst;

        PageHandler.copyMemory(src, dst, offset(srcIdx), offset(dstIdx), cnt * itemSize);
    }

    /** {@inheritDoc} */
    @Override protected final int offset(int idx) {
        assert idx >= 0: idx;

        return ITEMS_OFF + idx * itemSize;
    }
}
