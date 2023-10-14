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
import org.eclipse.milo.opcua.stack.core.types.builtin.OptionSetUI8;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;

/**
 * @see <a href="https://reference.opcfoundation.org/v105/Core/docs/Part3/8.59">https://reference.opcfoundation.org/v105/Core/docs/Part3/8.59</a>
 */
@EqualsAndHashCode(
    callSuper = true
)
@ToString
public class EventNotifierType extends OptionSetUI8<EventNotifierType.Field> {
    public EventNotifierType(UByte value) {
        super(value);
    }

    public boolean getSubscribeToEvents() {
        return get(Field.SubscribeToEvents);
    }

    public boolean getHistoryRead() {
        return get(Field.HistoryRead);
    }

    public boolean getHistoryWrite() {
        return get(Field.HistoryWrite);
    }

    @Override
    public UByte getValue() {
        return (UByte) value;
    }

    @Override
    public Set<Field> toSet() {
        return Arrays.stream(Field.values())
            .filter(this::get)
            .collect(Collectors.toSet());
    }

    public static EventNotifierType of(Field... fields) {
        long bits = 0L;

        for (Field f : fields) {
            bits |= (1L << f.bitIndex);
        }

        return new EventNotifierType(UByte.valueOf(bits));
    }

    public enum Field implements BitIndex {
        SubscribeToEvents(0),

        HistoryRead(2),

        HistoryWrite(3);

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
