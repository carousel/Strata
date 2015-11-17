/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.basics.market;

import static com.opengamma.strata.collect.TestHelper.assertSerialization;
import static com.opengamma.strata.collect.TestHelper.assertThrowsIllegalArg;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Map;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.collect.timeseries.LocalDateDoubleTimeSeries;

@Test
public class ImmutableMarketDataTest {

  private static final TestObservableKey KEY1 = TestObservableKey.of("1");
  private static final TestObservableKey KEY2 = TestObservableKey.of("2");
  private static final LocalDateDoubleTimeSeries TIME_SERIES = LocalDateDoubleTimeSeries.builder()
      .put(LocalDate.of(2011, 3, 8), 1.1)
      .put(LocalDate.of(2011, 3, 10), 1.2)
      .build();
  private static final ImmutableMarketData DATA = data();

  public void containsValue() {
    assertThat(DATA.containsValue(KEY1)).isTrue();
    assertThat(DATA.containsValue(KEY2)).isFalse();
  }

  public void containsTimeSeries() {
    assertThat(DATA.containsTimeSeries(KEY1)).isFalse();
    assertThat(DATA.containsTimeSeries(KEY2)).isTrue();
  }

  public void getValue() {
    assertThat(DATA.getValue(KEY1)).isEqualTo(123d);
    assertThrowsIllegalArg(() -> DATA.getValue(KEY2));
  }

  public void getTimeSeries() {
    assertThrowsIllegalArg(() -> DATA.getTimeSeries(KEY1));
    assertThat(DATA.getTimeSeries(KEY2)).isEqualTo(TIME_SERIES);
  }

  public void serialization() {
    assertSerialization(data());
  }

  private static ImmutableMarketData data() {
    Map<? extends MarketDataKey<?>, Object> dataMap = ImmutableMap.of(KEY1, 123d);
    Map<ObservableKey, LocalDateDoubleTimeSeries> timeSeriesMap = ImmutableMap.of(KEY2, TIME_SERIES);
    return ImmutableMarketData.builder().values(dataMap).timeSeries(timeSeriesMap).build();
  }
}