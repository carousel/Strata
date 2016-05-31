/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.calc.marketdata;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableDefaults;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.data.MarketDataId;
import com.opengamma.strata.data.ObservableId;
import com.opengamma.strata.data.ObservableSource;

/**
 * Specifies the market data required for a function to perform a calculation.
 */
@BeanDefinition
public final class FunctionRequirements implements ImmutableBean {

  /**
   * An empty set of requirements.
   */
  private static final FunctionRequirements EMPTY = FunctionRequirements.builder().build();

  /**
   * The market data identifiers of the values required for the calculation.
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableSet<? extends MarketDataId<?>> singleValueRequirements;
  /** 
   * The market data identifiers of the time-series of required for the calculation.
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableSet<ObservableId> timeSeriesRequirements;
  /**
   * The currencies used in the calculation results.
   * <p>
   * This cause FX rates to be requested that allow conversion between the currencies
   * specified and the reporting currency.
   * It will be possible to obtain any FX rate pair for these currencies.
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableSet<Currency> outputCurrencies;
  /**
   * The source of market data for FX, quotes and other observable market data.
   * <p>
   * This is used to control the source of observable market data.
   * By default, this will be {@link ObservableSource#NONE}.
   */
  @PropertyDefinition(validate = "notNull")
  private final ObservableSource observableSource;

  //-------------------------------------------------------------------------
  /**
   * Returns an empty set of requirements.
   *
   * @return an empty set of requirements
   */
  public static FunctionRequirements empty() {
    return EMPTY;
  }

  @ImmutableDefaults
  private static void applyDefaults(Builder builder) {
    builder.observableSource = ObservableSource.NONE;
  }

  //-------------------------------------------------------------------------
  /**
   * Combines these requirements with another set.
   * <p>
   * The result contains the union of the two sets of requirements.
   *
   * @param other  the other requirements
   * @return the combined requirements
   */
  public FunctionRequirements combinedWith(FunctionRequirements other) {
    return builder()
        .singleValueRequirements(Sets.union(singleValueRequirements, other.singleValueRequirements))
        .timeSeriesRequirements(Sets.union(timeSeriesRequirements, other.timeSeriesRequirements))
        .outputCurrencies(Sets.union(outputCurrencies, other.outputCurrencies))
        .observableSource(!this.observableSource.equals(ObservableSource.NONE) ? this.observableSource : other.observableSource)
        .build();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FunctionRequirements}.
   * @return the meta-bean, not null
   */
  public static FunctionRequirements.Meta meta() {
    return FunctionRequirements.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(FunctionRequirements.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static FunctionRequirements.Builder builder() {
    return new FunctionRequirements.Builder();
  }

  private FunctionRequirements(
      Set<? extends MarketDataId<?>> singleValueRequirements,
      Set<ObservableId> timeSeriesRequirements,
      Set<Currency> outputCurrencies,
      ObservableSource observableSource) {
    JodaBeanUtils.notNull(singleValueRequirements, "singleValueRequirements");
    JodaBeanUtils.notNull(timeSeriesRequirements, "timeSeriesRequirements");
    JodaBeanUtils.notNull(outputCurrencies, "outputCurrencies");
    JodaBeanUtils.notNull(observableSource, "observableSource");
    this.singleValueRequirements = ImmutableSet.copyOf(singleValueRequirements);
    this.timeSeriesRequirements = ImmutableSet.copyOf(timeSeriesRequirements);
    this.outputCurrencies = ImmutableSet.copyOf(outputCurrencies);
    this.observableSource = observableSource;
  }

  @Override
  public FunctionRequirements.Meta metaBean() {
    return FunctionRequirements.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the market data identifiers of the values required for the calculation.
   * @return the value of the property, not null
   */
  public ImmutableSet<? extends MarketDataId<?>> getSingleValueRequirements() {
    return singleValueRequirements;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the market data identifiers of the time-series of required for the calculation.
   * @return the value of the property, not null
   */
  public ImmutableSet<ObservableId> getTimeSeriesRequirements() {
    return timeSeriesRequirements;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currencies used in the calculation results.
   * <p>
   * This cause FX rates to be requested that allow conversion between the currencies
   * specified and the reporting currency.
   * It will be possible to obtain any FX rate pair for these currencies.
   * @return the value of the property, not null
   */
  public ImmutableSet<Currency> getOutputCurrencies() {
    return outputCurrencies;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the source of market data for FX, quotes and other observable market data.
   * <p>
   * This is used to control the source of observable market data.
   * By default, this will be {@link ObservableSource#NONE}.
   * @return the value of the property, not null
   */
  public ObservableSource getObservableSource() {
    return observableSource;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FunctionRequirements other = (FunctionRequirements) obj;
      return JodaBeanUtils.equal(singleValueRequirements, other.singleValueRequirements) &&
          JodaBeanUtils.equal(timeSeriesRequirements, other.timeSeriesRequirements) &&
          JodaBeanUtils.equal(outputCurrencies, other.outputCurrencies) &&
          JodaBeanUtils.equal(observableSource, other.observableSource);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(singleValueRequirements);
    hash = hash * 31 + JodaBeanUtils.hashCode(timeSeriesRequirements);
    hash = hash * 31 + JodaBeanUtils.hashCode(outputCurrencies);
    hash = hash * 31 + JodaBeanUtils.hashCode(observableSource);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("FunctionRequirements{");
    buf.append("singleValueRequirements").append('=').append(singleValueRequirements).append(',').append(' ');
    buf.append("timeSeriesRequirements").append('=').append(timeSeriesRequirements).append(',').append(' ');
    buf.append("outputCurrencies").append('=').append(outputCurrencies).append(',').append(' ');
    buf.append("observableSource").append('=').append(JodaBeanUtils.toString(observableSource));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FunctionRequirements}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code singleValueRequirements} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableSet<? extends MarketDataId<?>>> singleValueRequirements = DirectMetaProperty.ofImmutable(
        this, "singleValueRequirements", FunctionRequirements.class, (Class) ImmutableSet.class);
    /**
     * The meta-property for the {@code timeSeriesRequirements} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableSet<ObservableId>> timeSeriesRequirements = DirectMetaProperty.ofImmutable(
        this, "timeSeriesRequirements", FunctionRequirements.class, (Class) ImmutableSet.class);
    /**
     * The meta-property for the {@code outputCurrencies} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableSet<Currency>> outputCurrencies = DirectMetaProperty.ofImmutable(
        this, "outputCurrencies", FunctionRequirements.class, (Class) ImmutableSet.class);
    /**
     * The meta-property for the {@code observableSource} property.
     */
    private final MetaProperty<ObservableSource> observableSource = DirectMetaProperty.ofImmutable(
        this, "observableSource", FunctionRequirements.class, ObservableSource.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "singleValueRequirements",
        "timeSeriesRequirements",
        "outputCurrencies",
        "observableSource");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -442841799:  // singleValueRequirements
          return singleValueRequirements;
        case -1437279660:  // timeSeriesRequirements
          return timeSeriesRequirements;
        case -1022597040:  // outputCurrencies
          return outputCurrencies;
        case 1793526590:  // observableSource
          return observableSource;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public FunctionRequirements.Builder builder() {
      return new FunctionRequirements.Builder();
    }

    @Override
    public Class<? extends FunctionRequirements> beanType() {
      return FunctionRequirements.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code singleValueRequirements} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableSet<? extends MarketDataId<?>>> singleValueRequirements() {
      return singleValueRequirements;
    }

    /**
     * The meta-property for the {@code timeSeriesRequirements} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableSet<ObservableId>> timeSeriesRequirements() {
      return timeSeriesRequirements;
    }

    /**
     * The meta-property for the {@code outputCurrencies} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableSet<Currency>> outputCurrencies() {
      return outputCurrencies;
    }

    /**
     * The meta-property for the {@code observableSource} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ObservableSource> observableSource() {
      return observableSource;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -442841799:  // singleValueRequirements
          return ((FunctionRequirements) bean).getSingleValueRequirements();
        case -1437279660:  // timeSeriesRequirements
          return ((FunctionRequirements) bean).getTimeSeriesRequirements();
        case -1022597040:  // outputCurrencies
          return ((FunctionRequirements) bean).getOutputCurrencies();
        case 1793526590:  // observableSource
          return ((FunctionRequirements) bean).getObservableSource();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code FunctionRequirements}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<FunctionRequirements> {

    private Set<? extends MarketDataId<?>> singleValueRequirements = ImmutableSet.of();
    private Set<ObservableId> timeSeriesRequirements = ImmutableSet.of();
    private Set<Currency> outputCurrencies = ImmutableSet.of();
    private ObservableSource observableSource;

    /**
     * Restricted constructor.
     */
    private Builder() {
      applyDefaults(this);
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(FunctionRequirements beanToCopy) {
      this.singleValueRequirements = beanToCopy.getSingleValueRequirements();
      this.timeSeriesRequirements = beanToCopy.getTimeSeriesRequirements();
      this.outputCurrencies = beanToCopy.getOutputCurrencies();
      this.observableSource = beanToCopy.getObservableSource();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -442841799:  // singleValueRequirements
          return singleValueRequirements;
        case -1437279660:  // timeSeriesRequirements
          return timeSeriesRequirements;
        case -1022597040:  // outputCurrencies
          return outputCurrencies;
        case 1793526590:  // observableSource
          return observableSource;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -442841799:  // singleValueRequirements
          this.singleValueRequirements = (Set<? extends MarketDataId<?>>) newValue;
          break;
        case -1437279660:  // timeSeriesRequirements
          this.timeSeriesRequirements = (Set<ObservableId>) newValue;
          break;
        case -1022597040:  // outputCurrencies
          this.outputCurrencies = (Set<Currency>) newValue;
          break;
        case 1793526590:  // observableSource
          this.observableSource = (ObservableSource) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public FunctionRequirements build() {
      return new FunctionRequirements(
          singleValueRequirements,
          timeSeriesRequirements,
          outputCurrencies,
          observableSource);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the market data identifiers of the values required for the calculation.
     * @param singleValueRequirements  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder singleValueRequirements(Set<? extends MarketDataId<?>> singleValueRequirements) {
      JodaBeanUtils.notNull(singleValueRequirements, "singleValueRequirements");
      this.singleValueRequirements = singleValueRequirements;
      return this;
    }

    /**
     * Sets the {@code singleValueRequirements} property in the builder
     * from an array of objects.
     * @param singleValueRequirements  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder singleValueRequirements(MarketDataId<?>... singleValueRequirements) {
      return singleValueRequirements(ImmutableSet.copyOf(singleValueRequirements));
    }

    /**
     * Sets the market data identifiers of the time-series of required for the calculation.
     * @param timeSeriesRequirements  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder timeSeriesRequirements(Set<ObservableId> timeSeriesRequirements) {
      JodaBeanUtils.notNull(timeSeriesRequirements, "timeSeriesRequirements");
      this.timeSeriesRequirements = timeSeriesRequirements;
      return this;
    }

    /**
     * Sets the {@code timeSeriesRequirements} property in the builder
     * from an array of objects.
     * @param timeSeriesRequirements  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder timeSeriesRequirements(ObservableId... timeSeriesRequirements) {
      return timeSeriesRequirements(ImmutableSet.copyOf(timeSeriesRequirements));
    }

    /**
     * Sets the currencies used in the calculation results.
     * <p>
     * This cause FX rates to be requested that allow conversion between the currencies
     * specified and the reporting currency.
     * It will be possible to obtain any FX rate pair for these currencies.
     * @param outputCurrencies  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder outputCurrencies(Set<Currency> outputCurrencies) {
      JodaBeanUtils.notNull(outputCurrencies, "outputCurrencies");
      this.outputCurrencies = outputCurrencies;
      return this;
    }

    /**
     * Sets the {@code outputCurrencies} property in the builder
     * from an array of objects.
     * @param outputCurrencies  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder outputCurrencies(Currency... outputCurrencies) {
      return outputCurrencies(ImmutableSet.copyOf(outputCurrencies));
    }

    /**
     * Sets the source of market data for FX, quotes and other observable market data.
     * <p>
     * This is used to control the source of observable market data.
     * By default, this will be {@link ObservableSource#NONE}.
     * @param observableSource  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder observableSource(ObservableSource observableSource) {
      JodaBeanUtils.notNull(observableSource, "observableSource");
      this.observableSource = observableSource;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("FunctionRequirements.Builder{");
      buf.append("singleValueRequirements").append('=').append(JodaBeanUtils.toString(singleValueRequirements)).append(',').append(' ');
      buf.append("timeSeriesRequirements").append('=').append(JodaBeanUtils.toString(timeSeriesRequirements)).append(',').append(' ');
      buf.append("outputCurrencies").append('=').append(JodaBeanUtils.toString(outputCurrencies)).append(',').append(' ');
      buf.append("observableSource").append('=').append(JodaBeanUtils.toString(observableSource));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
