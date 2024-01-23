/*
 * Copyright (c) 2024 the Eclipse Milo Authors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.milo.opcua.stack.core.types.structured;

import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.StringJoiner;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.eclipse.milo.opcua.stack.core.NamespaceTable;
import org.eclipse.milo.opcua.stack.core.encoding.EncodingContext;
import org.eclipse.milo.opcua.stack.core.encoding.GenericDataTypeCodec;
import org.eclipse.milo.opcua.stack.core.encoding.UaDecoder;
import org.eclipse.milo.opcua.stack.core.encoding.UaEncoder;
import org.eclipse.milo.opcua.stack.core.types.UaStructuredType;
import org.eclipse.milo.opcua.stack.core.types.builtin.ExpandedNodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.StructureType;
import org.jetbrains.annotations.Nullable;

/**
 * @see <a href="https://reference.opcfoundation.org/v105/Core/docs/Part5/12.3.15/#12.3.15.1">https://reference.opcfoundation.org/v105/Core/docs/Part5/12.3.15/#12.3.15.1</a>
 */
@EqualsAndHashCode(
    callSuper = true
)
@SuperBuilder
public class AnonymousIdentityToken extends UserIdentityToken implements UaStructuredType {
    public static final ExpandedNodeId TYPE_ID = ExpandedNodeId.parse("ns=0;i=319");

    public static final ExpandedNodeId BINARY_ENCODING_ID = ExpandedNodeId.parse("i=321");

    public static final ExpandedNodeId XML_ENCODING_ID = ExpandedNodeId.parse("i=320");

    public static final ExpandedNodeId JSON_ENCODING_ID = ExpandedNodeId.parse("i=15141");

    public AnonymousIdentityToken(@Nullable String policyId) {
        super(policyId);
    }

    @Override
    public ExpandedNodeId getTypeId() {
        return TYPE_ID;
    }

    @Override
    public ExpandedNodeId getBinaryEncodingId() {
        return BINARY_ENCODING_ID;
    }

    @Override
    public ExpandedNodeId getXmlEncodingId() {
        return XML_ENCODING_ID;
    }

    @Override
    public ExpandedNodeId getJsonEncodingId() {
        return JSON_ENCODING_ID;
    }

    @Override
    public String toString() {
        var joiner = new StringJoiner(", ", AnonymousIdentityToken.class.getSimpleName() + "[", "]");
        return joiner.toString();
    }

    public static StructureDefinition definition(NamespaceTable namespaceTable) {
        return new StructureDefinition(
            new NodeId(0, 321),
            new NodeId(0, 316),
            StructureType.Structure,
            new StructureField[]{
                new StructureField("PolicyId", LocalizedText.NULL_VALUE, new NodeId(0, 12), -1, null, UInteger.valueOf(0), false)
            }
        );
    }

    public static final class Codec extends GenericDataTypeCodec<AnonymousIdentityToken> {
        @Override
        public Class<AnonymousIdentityToken> getType() {
            return AnonymousIdentityToken.class;
        }

        @Override
        public AnonymousIdentityToken decodeType(EncodingContext context, UaDecoder decoder) {
            String policyId = decoder.decodeString("PolicyId");
            return new AnonymousIdentityToken(policyId);
        }

        @Override
        public void encodeType(EncodingContext context, UaEncoder encoder,
                               AnonymousIdentityToken value) {
            encoder.encodeString("PolicyId", value.getPolicyId());
        }
    }
}
