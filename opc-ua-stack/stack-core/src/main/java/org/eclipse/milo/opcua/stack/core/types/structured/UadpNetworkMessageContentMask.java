/*
 * Copyright (c) 2023 the Eclipse Milo Authors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.milo.opcua.stack.core.types.structured;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.eclipse.milo.opcua.stack.core.types.builtin.OptionSetUI32;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;

/**
 * @see <a href="https://reference.opcfoundation.org/v105/Core/docs/Part14/6.3.1/#6.3.1.1.4">https://reference.opcfoundation.org/v105/Core/docs/Part14/6.3.1/#6.3.1.1.4</a>
 */
@EqualsAndHashCode(
    callSuper = true
)
@ToString
public class UadpNetworkMessageContentMask extends OptionSetUI32<UadpNetworkMessageContentMask.Field> {
    public UadpNetworkMessageContentMask(UInteger value) {
        super(value);
    }

    public boolean getPublisherId() {
        return get(Field.PublisherId);
    }

    public boolean getGroupHeader() {
        return get(Field.GroupHeader);
    }

    public boolean getWriterGroupId() {
        return get(Field.WriterGroupId);
    }

    public boolean getGroupVersion() {
        return get(Field.GroupVersion);
    }

    public boolean getNetworkMessageNumber() {
        return get(Field.NetworkMessageNumber);
    }

    public boolean getSequenceNumber() {
        return get(Field.SequenceNumber);
    }

    public boolean getPayloadHeader() {
        return get(Field.PayloadHeader);
    }

    public boolean getTimestamp() {
        return get(Field.Timestamp);
    }

    public boolean getPicoSeconds() {
        return get(Field.PicoSeconds);
    }

    public boolean getDataSetClassId() {
        return get(Field.DataSetClassId);
    }

    public boolean getPromotedFields() {
        return get(Field.PromotedFields);
    }

    @Override
    public UInteger getValue() {
        return (UInteger) value;
    }

    @Override
    public Set<Field> toSet() {
        return Arrays.stream(Field.values())
            .filter(this::get)
            .collect(Collectors.toSet());
    }

    public static UadpNetworkMessageContentMask of(Field... fields) {
        long bits = 0L;

        for (Field f : fields) {
            bits |= (1L << f.bitIndex);
        }

        return new UadpNetworkMessageContentMask(UInteger.valueOf(bits));
    }

    public enum Field implements BitIndex {
        PublisherId(0),

        GroupHeader(1),

        WriterGroupId(2),

        GroupVersion(3),

        NetworkMessageNumber(4),

        SequenceNumber(5),

        PayloadHeader(6),

        Timestamp(7),

        PicoSeconds(8),

        DataSetClassId(9),

        PromotedFields(10);

        private final int bitIndex;

        Field(int bitIndex) {
            this.bitIndex = bitIndex;
        }

        @Override
        public int getBitIndex() {
            return bitIndex;
        }
    }
}
