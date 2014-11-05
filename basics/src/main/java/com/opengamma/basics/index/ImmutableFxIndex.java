/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.basics.index;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.basics.currency.CurrencyPair;
import com.opengamma.basics.date.DaysAdjustment;
import com.opengamma.basics.date.HolidayCalendar;
import com.opengamma.basics.date.HolidayCalendars;
import com.opengamma.basics.index.FxIndex;
import com.opengamma.collect.ArgChecker;

/**
 * A foreign exchange index implementation based on an immutable set of rules.
 * <p>
 * A standard immutable implementation of {@link FxIndex} that defines the currency pair
 * and the rule for converting from fixing to maturity.
 * <p>
 * In most cases, applications should refer to indices by name, using {@link FxIndex#of(String)}.
 * The named index will typically be resolved to an instance of this class.
 * As such, it is recommended to use the {@code FxIndex} interface in application
 * code rather than directly referring to this class.
 */
@BeanDefinition
public final class ImmutableFxIndex
    implements FxIndex, ImmutableBean, Serializable {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The calendar name.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final String name;
  /**
   * The currency pair.
   * <p>
   * When the index is queried, the result equates the this base-counter order.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final CurrencyPair currencyPair;
  /**
   * The calendar that the fixing date follows.
   * <p>
   * The fixing date is when the rate is determined.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final HolidayCalendar fixingCalendar;
  /**
   * The adjustment applied to the fixing date to obtain the maturity date.
   * <p>
   * The maturity date is the start date of the indexed deposit.
   * In most cases, the maturity date is 2 days after the fixing date.
   */
  @PropertyDefinition(validate = "notNull")
  private final DaysAdjustment maturityDateOffset;

  //-----------------------------------------------------------------------
  @Override
  public LocalDate calculateMaturityFromFixing(LocalDate fixingDate) {
    ArgChecker.notNull(fixingDate, "fixingDate");
    LocalDate fixingBusinessDay = fixingCalendar.nextOrSame(fixingDate);
    return maturityDateOffset.adjust(fixingBusinessDay);
  }

  @Override
  public LocalDate calculateFixingFromMaturity(LocalDate maturityDate) {
    ArgChecker.notNull(maturityDate, "maturityDate");
    LocalDate effectiveBusinessDay = maturityDateCalendar().nextOrSame(maturityDate);
    LocalDate fixingDate = effectiveBusinessDay;
    while (maturityDateOffset.adjust(fixingDate).isAfter(effectiveBusinessDay) || fixingCalendar.isHoliday(fixingDate)) {
      fixingDate = fixingDate.minusDays(1);
    }
    return fixingDate;
  }

  // finds the calendar of the maturity date
  private HolidayCalendar maturityDateCalendar() {
    HolidayCalendar cal = maturityDateOffset.getEffectiveResultCalendar();
    if (cal == HolidayCalendars.NO_HOLIDAYS) {
      cal = fixingCalendar;
    }
    return cal;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns the name of the calendar.
   * 
   * @return the descriptive string
   */
  @Override
  public String toString() {
    return getName();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ImmutableFxIndex}.
   * @return the meta-bean, not null
   */
  public static ImmutableFxIndex.Meta meta() {
    return ImmutableFxIndex.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ImmutableFxIndex.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static ImmutableFxIndex.Builder builder() {
    return new ImmutableFxIndex.Builder();
  }

  private ImmutableFxIndex(
      String name,
      CurrencyPair currencyPair,
      HolidayCalendar fixingCalendar,
      DaysAdjustment maturityDateOffset) {
    JodaBeanUtils.notNull(name, "name");
    JodaBeanUtils.notNull(currencyPair, "currencyPair");
    JodaBeanUtils.notNull(fixingCalendar, "fixingCalendar");
    JodaBeanUtils.notNull(maturityDateOffset, "maturityDateOffset");
    this.name = name;
    this.currencyPair = currencyPair;
    this.fixingCalendar = fixingCalendar;
    this.maturityDateOffset = maturityDateOffset;
  }

  @Override
  public ImmutableFxIndex.Meta metaBean() {
    return ImmutableFxIndex.Meta.INSTANCE;
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
   * Gets the calendar name.
   * @return the value of the property, not null
   */
  @Override
  public String getName() {
    return name;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency pair.
   * <p>
   * When the index is queried, the result equates the this base-counter order.
   * @return the value of the property, not null
   */
  @Override
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the calendar that the fixing date follows.
   * <p>
   * The fixing date is when the rate is determined.
   * @return the value of the property, not null
   */
  @Override
  public HolidayCalendar getFixingCalendar() {
    return fixingCalendar;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the adjustment applied to the fixing date to obtain the maturity date.
   * <p>
   * The maturity date is the start date of the indexed deposit.
   * In most cases, the maturity date is 2 days after the fixing date.
   * @return the value of the property, not null
   */
  public DaysAdjustment getMaturityDateOffset() {
    return maturityDateOffset;
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
      ImmutableFxIndex other = (ImmutableFxIndex) obj;
      return JodaBeanUtils.equal(getName(), other.getName()) &&
          JodaBeanUtils.equal(getCurrencyPair(), other.getCurrencyPair()) &&
          JodaBeanUtils.equal(getFixingCalendar(), other.getFixingCalendar()) &&
          JodaBeanUtils.equal(getMaturityDateOffset(), other.getMaturityDateOffset());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurrencyPair());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFixingCalendar());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMaturityDateOffset());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ImmutableFxIndex}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> name = DirectMetaProperty.ofImmutable(
        this, "name", ImmutableFxIndex.class, String.class);
    /**
     * The meta-property for the {@code currencyPair} property.
     */
    private final MetaProperty<CurrencyPair> currencyPair = DirectMetaProperty.ofImmutable(
        this, "currencyPair", ImmutableFxIndex.class, CurrencyPair.class);
    /**
     * The meta-property for the {@code fixingCalendar} property.
     */
    private final MetaProperty<HolidayCalendar> fixingCalendar = DirectMetaProperty.ofImmutable(
        this, "fixingCalendar", ImmutableFxIndex.class, HolidayCalendar.class);
    /**
     * The meta-property for the {@code maturityDateOffset} property.
     */
    private final MetaProperty<DaysAdjustment> maturityDateOffset = DirectMetaProperty.ofImmutable(
        this, "maturityDateOffset", ImmutableFxIndex.class, DaysAdjustment.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "name",
        "currencyPair",
        "fixingCalendar",
        "maturityDateOffset");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return name;
        case 1005147787:  // currencyPair
          return currencyPair;
        case 394230283:  // fixingCalendar
          return fixingCalendar;
        case 1574797394:  // maturityDateOffset
          return maturityDateOffset;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ImmutableFxIndex.Builder builder() {
      return new ImmutableFxIndex.Builder();
    }

    @Override
    public Class<? extends ImmutableFxIndex> beanType() {
      return ImmutableFxIndex.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public MetaProperty<String> name() {
      return name;
    }

    /**
     * The meta-property for the {@code currencyPair} property.
     * @return the meta-property, not null
     */
    public MetaProperty<CurrencyPair> currencyPair() {
      return currencyPair;
    }

    /**
     * The meta-property for the {@code fixingCalendar} property.
     * @return the meta-property, not null
     */
    public MetaProperty<HolidayCalendar> fixingCalendar() {
      return fixingCalendar;
    }

    /**
     * The meta-property for the {@code maturityDateOffset} property.
     * @return the meta-property, not null
     */
    public MetaProperty<DaysAdjustment> maturityDateOffset() {
      return maturityDateOffset;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return ((ImmutableFxIndex) bean).getName();
        case 1005147787:  // currencyPair
          return ((ImmutableFxIndex) bean).getCurrencyPair();
        case 394230283:  // fixingCalendar
          return ((ImmutableFxIndex) bean).getFixingCalendar();
        case 1574797394:  // maturityDateOffset
          return ((ImmutableFxIndex) bean).getMaturityDateOffset();
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
   * The bean-builder for {@code ImmutableFxIndex}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<ImmutableFxIndex> {

    private String name;
    private CurrencyPair currencyPair;
    private HolidayCalendar fixingCalendar;
    private DaysAdjustment maturityDateOffset;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(ImmutableFxIndex beanToCopy) {
      this.name = beanToCopy.getName();
      this.currencyPair = beanToCopy.getCurrencyPair();
      this.fixingCalendar = beanToCopy.getFixingCalendar();
      this.maturityDateOffset = beanToCopy.getMaturityDateOffset();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return name;
        case 1005147787:  // currencyPair
          return currencyPair;
        case 394230283:  // fixingCalendar
          return fixingCalendar;
        case 1574797394:  // maturityDateOffset
          return maturityDateOffset;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          this.name = (String) newValue;
          break;
        case 1005147787:  // currencyPair
          this.currencyPair = (CurrencyPair) newValue;
          break;
        case 394230283:  // fixingCalendar
          this.fixingCalendar = (HolidayCalendar) newValue;
          break;
        case 1574797394:  // maturityDateOffset
          this.maturityDateOffset = (DaysAdjustment) newValue;
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
    public ImmutableFxIndex build() {
      return new ImmutableFxIndex(
          name,
          currencyPair,
          fixingCalendar,
          maturityDateOffset);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code name} property in the builder.
     * @param name  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder name(String name) {
      JodaBeanUtils.notNull(name, "name");
      this.name = name;
      return this;
    }

    /**
     * Sets the {@code currencyPair} property in the builder.
     * @param currencyPair  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder currencyPair(CurrencyPair currencyPair) {
      JodaBeanUtils.notNull(currencyPair, "currencyPair");
      this.currencyPair = currencyPair;
      return this;
    }

    /**
     * Sets the {@code fixingCalendar} property in the builder.
     * @param fixingCalendar  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder fixingCalendar(HolidayCalendar fixingCalendar) {
      JodaBeanUtils.notNull(fixingCalendar, "fixingCalendar");
      this.fixingCalendar = fixingCalendar;
      return this;
    }

    /**
     * Sets the {@code maturityDateOffset} property in the builder.
     * @param maturityDateOffset  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder maturityDateOffset(DaysAdjustment maturityDateOffset) {
      JodaBeanUtils.notNull(maturityDateOffset, "maturityDateOffset");
      this.maturityDateOffset = maturityDateOffset;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("ImmutableFxIndex.Builder{");
      buf.append("name").append('=').append(JodaBeanUtils.toString(name)).append(',').append(' ');
      buf.append("currencyPair").append('=').append(JodaBeanUtils.toString(currencyPair)).append(',').append(' ');
      buf.append("fixingCalendar").append('=').append(JodaBeanUtils.toString(fixingCalendar)).append(',').append(' ');
      buf.append("maturityDateOffset").append('=').append(JodaBeanUtils.toString(maturityDateOffset));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
