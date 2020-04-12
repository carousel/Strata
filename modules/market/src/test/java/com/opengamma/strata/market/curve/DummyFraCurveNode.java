/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.curve;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.TypedMetaBean;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.light.LightMetaBean;

import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.date.HolidayCalendars;
import com.opengamma.strata.basics.index.IborIndex;
import com.opengamma.strata.data.MarketData;
import com.opengamma.strata.data.ObservableId;
import com.opengamma.strata.market.ValueType;
import com.opengamma.strata.market.param.DatedParameterMetadata;
import com.opengamma.strata.market.param.LabelDateParameterMetadata;

/**
 * Dummy curve node.
 * Based on a FRA.
 */
@BeanDefinition(style = "light")
public final class DummyFraCurveNode
    implements CurveNode, ImmutableBean, Serializable {

  @PropertyDefinition(validate = "notNull")
  private final Period periodToStart;
  @PropertyDefinition(validate = "notNull")
  private final Period periodToEnd;
  @PropertyDefinition(validate = "notNull")
  private final ObservableId rateId;
  @PropertyDefinition
  private final double spread;
  @PropertyDefinition(validate = "notEmpty", overrideGet = true)
  private final String label;
  @PropertyDefinition(validate = "notNull")
  private final CurveNodeDateOrder order;

  //-------------------------------------------------------------------------
  public static DummyFraCurveNode of(Period periodToStart, IborIndex index, ObservableId rateId) {
    return new DummyFraCurveNode(
        periodToStart,
        periodToStart.plus(index.getTenor().getPeriod()),
        rateId,
        0,
        "Dummy:" + periodToStart,
        CurveNodeDateOrder.DEFAULT);
  }

  public static DummyFraCurveNode of(Period periodToStart, IborIndex index, ObservableId rateId, double spread) {
    return new DummyFraCurveNode(
        periodToStart,
        periodToStart.plus(index.getTenor().getPeriod()),
        rateId,
        spread,
        "Dummy:" + periodToStart,
        CurveNodeDateOrder.DEFAULT);
  }

  public static DummyFraCurveNode of(Period periodToStart, IborIndex index, ObservableId rateId, CurveNodeDateOrder order) {
    return new DummyFraCurveNode(
        periodToStart,
        periodToStart.plus(index.getTenor().getPeriod()),
        rateId,
        0,
        "Dummy:" + periodToStart,
        order);
  }

  //-------------------------------------------------------------------------
  @Override
  public Set<ObservableId> requirements() {
    return ImmutableSet.of(rateId);
  }

  @Override
  public LocalDate date(LocalDate valuationDate, ReferenceData refData) {
    return HolidayCalendars.SAT_SUN.nextOrSame(valuationDate.plus(periodToEnd));
  }

  @Override
  public DatedParameterMetadata metadata(LocalDate valuationDate, ReferenceData refData) {
    return LabelDateParameterMetadata.of(
        HolidayCalendars.SAT_SUN.nextOrSame(valuationDate.plus(periodToEnd)), periodToEnd.toString());
  }

  @Override
  public DummyFraTrade trade(double quantity, MarketData marketData, ReferenceData refData) {
    double fixedRate = marketData.getValue(rateId) + spread;
    return DummyFraTrade.of(marketData.getValuationDate(), fixedRate);
  }

  @Override
  public DummyFraTrade resolvedTrade(double quantity, MarketData marketData, ReferenceData refData) {
    return trade(quantity, marketData, refData);
  }

  @Override
  public double initialGuess(MarketData marketData, ValueType valueType) {
    if (ValueType.ZERO_RATE.equals(valueType)) {
      return marketData.getValue(rateId);
    }
    return 0d;
  }

  @Override
  public CurveNodeDateOrder getDateOrder() {
    return order;
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code DummyFraCurveNode}.
   */
  private static final TypedMetaBean<DummyFraCurveNode> META_BEAN =
      LightMetaBean.of(
          DummyFraCurveNode.class,
          MethodHandles.lookup(),
          new String[] {
              "periodToStart",
              "periodToEnd",
              "rateId",
              "spread",
              "label",
              "order"},
          new Object[0]);

  /**
   * The meta-bean for {@code DummyFraCurveNode}.
   * @return the meta-bean, not null
   */
  public static TypedMetaBean<DummyFraCurveNode> meta() {
    return META_BEAN;
  }

  static {
    MetaBean.register(META_BEAN);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private DummyFraCurveNode(
      Period periodToStart,
      Period periodToEnd,
      ObservableId rateId,
      double spread,
      String label,
      CurveNodeDateOrder order) {
    JodaBeanUtils.notNull(periodToStart, "periodToStart");
    JodaBeanUtils.notNull(periodToEnd, "periodToEnd");
    JodaBeanUtils.notNull(rateId, "rateId");
    JodaBeanUtils.notEmpty(label, "label");
    JodaBeanUtils.notNull(order, "order");
    this.periodToStart = periodToStart;
    this.periodToEnd = periodToEnd;
    this.rateId = rateId;
    this.spread = spread;
    this.label = label;
    this.order = order;
  }

  @Override
  public TypedMetaBean<DummyFraCurveNode> metaBean() {
    return META_BEAN;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the periodToStart.
   * @return the value of the property, not null
   */
  public Period getPeriodToStart() {
    return periodToStart;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the periodToEnd.
   * @return the value of the property, not null
   */
  public Period getPeriodToEnd() {
    return periodToEnd;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the rateId.
   * @return the value of the property, not null
   */
  public ObservableId getRateId() {
    return rateId;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the spread.
   * @return the value of the property
   */
  public double getSpread() {
    return spread;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the label.
   * @return the value of the property, not empty
   */
  @Override
  public String getLabel() {
    return label;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the order.
   * @return the value of the property, not null
   */
  public CurveNodeDateOrder getOrder() {
    return order;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DummyFraCurveNode other = (DummyFraCurveNode) obj;
      return JodaBeanUtils.equal(periodToStart, other.periodToStart) &&
          JodaBeanUtils.equal(periodToEnd, other.periodToEnd) &&
          JodaBeanUtils.equal(rateId, other.rateId) &&
          JodaBeanUtils.equal(spread, other.spread) &&
          JodaBeanUtils.equal(label, other.label) &&
          JodaBeanUtils.equal(order, other.order);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(periodToStart);
    hash = hash * 31 + JodaBeanUtils.hashCode(periodToEnd);
    hash = hash * 31 + JodaBeanUtils.hashCode(rateId);
    hash = hash * 31 + JodaBeanUtils.hashCode(spread);
    hash = hash * 31 + JodaBeanUtils.hashCode(label);
    hash = hash * 31 + JodaBeanUtils.hashCode(order);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(224);
    buf.append("DummyFraCurveNode{");
    buf.append("periodToStart").append('=').append(JodaBeanUtils.toString(periodToStart)).append(',').append(' ');
    buf.append("periodToEnd").append('=').append(JodaBeanUtils.toString(periodToEnd)).append(',').append(' ');
    buf.append("rateId").append('=').append(JodaBeanUtils.toString(rateId)).append(',').append(' ');
    buf.append("spread").append('=').append(JodaBeanUtils.toString(spread)).append(',').append(' ');
    buf.append("label").append('=').append(JodaBeanUtils.toString(label)).append(',').append(' ');
    buf.append("order").append('=').append(JodaBeanUtils.toString(order));
    buf.append('}');
    return buf.toString();
  }

  //-------------------------- AUTOGENERATED END --------------------------
}
