/*
 * Copyright (c) 2025 the Eclipse Milo Authors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.milo.opcua.stack.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode.InfoType;
import org.junit.jupiter.api.Test;

class StatusCodesTest {

  @Test
  void lookupGood() {
    String[] ss = StatusCodes.lookup(StatusCodes.Good).orElseThrow();
    assertEquals("Good", ss[0]);
    assertEquals("The operation succeeded.", ss[1]);

    ss = StatusCodes.lookup(StatusCodes.Good_Cascade).orElseThrow();
    assertEquals("Good_Cascade", ss[0]);
    assertEquals(
        "The value is accurate, and the signal source supports cascade handshaking.", ss[1]);
  }

  @Test
  void lookupUncertain() {
    String[] ss = StatusCodes.lookup(StatusCodes.Uncertain).orElseThrow();
    assertEquals("Uncertain", ss[0]);
    assertEquals("The operation was uncertain.", ss[1]);

    ss = StatusCodes.lookup(StatusCodes.Uncertain_ConfigurationError).orElseThrow();
    assertEquals("Uncertain_ConfigurationError", ss[0]);
    assertEquals("The value may not be accurate due to a configuration issue.", ss[1]);
  }

  @Test
  void lookupBad() {
    String[] ss = StatusCodes.lookup(StatusCodes.Bad).orElseThrow();
    assertEquals("Bad", ss[0]);
    assertEquals("The operation failed.", ss[1]);

    ss = StatusCodes.lookup(StatusCodes.Bad_ServiceUnsupported).orElseThrow();
    assertEquals("Bad_ServiceUnsupported", ss[0]);
    assertEquals("The server does not support the requested service.", ss[1]);
  }

  @Test
  void infoBitsNotUsed() {
    assertSame(InfoType.NotUsed, StatusCode.GOOD.getInfoType());
  }

  @Test
  void infoBitsDataValue() {
    StatusCode statusCode = StatusCode.GOOD.withDataValueInfoType();
    assertSame(InfoType.DataValue, statusCode.getInfoType());
    assertEquals(
        0, statusCode.getDataValueInfoBits().map(StatusCode.DataValueInfoBits::bits).orElseThrow());

    assertSame(InfoType.NotUsed, statusCode.withoutDataValueInfoType().getInfoType());
  }

  @Test
  void infoBitsDataValueWithOverflow() {
    StatusCode withOverflow = StatusCode.GOOD.withOverflow();
    assertSame(InfoType.DataValue, withOverflow.getInfoType());
    assertTrue(
        withOverflow
            .getDataValueInfoBits()
            .map(StatusCode.DataValueInfoBits::isOverflow)
            .orElse(false));
    assertTrue(withOverflow.isOverflowSet());
  }

  @Test
  void toStringValue() {
    assertEquals(
        "StatusCode[name=Good, value=0x00000000, quality=good]", StatusCode.GOOD.toString());
    assertEquals(
        "StatusCode[name=Uncertain_InitialValue, value=0x40920000, quality=uncertain]",
        new StatusCode(StatusCodes.Uncertain_InitialValue).toString());
    assertEquals(
        "StatusCode[name=Bad_InternalError, value=0x80020000, quality=bad]",
        new StatusCode(StatusCodes.Bad_InternalError).toString());
  }
}
