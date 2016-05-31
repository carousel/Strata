/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.id;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
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

import com.opengamma.strata.data.MarketDataId;
import com.opengamma.strata.market.value.CdsRecoveryRate;
import com.opengamma.strata.product.credit.IndexReferenceInformation;

/**
 * Market data ID for a recovery rate to be used in the ISDA credit model's pricing for an index.
 */
@BeanDefinition(builderScope = "private")
public final class IsdaIndexRecoveryRateId
    implements MarketDataId<CdsRecoveryRate>, ImmutableBean, Serializable {

  /**
   * The information that identifies the single-name.
   */
  @PropertyDefinition(validate = "notNull")
  private final IndexReferenceInformation referenceInformation;

  //-------------------------------------------------------------------------
  /**
   * Creates an instance based on the reference information.
   *
   * @param referenceInformation  the information that identifies the single-name
   * @return the identifier
   */
  public static IsdaIndexRecoveryRateId of(IndexReferenceInformation referenceInformation) {
    return new IsdaIndexRecoveryRateId(referenceInformation);
  }

  //-------------------------------------------------------------------------
  @Override
  public Class<CdsRecoveryRate> getMarketDataType() {
    return CdsRecoveryRate.class;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code IsdaIndexRecoveryRateId}.
   * @return the meta-bean, not null
   */
  public static IsdaIndexRecoveryRateId.Meta meta() {
    return IsdaIndexRecoveryRateId.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(IsdaIndexRecoveryRateId.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private IsdaIndexRecoveryRateId(
      IndexReferenceInformation referenceInformation) {
    JodaBeanUtils.notNull(referenceInformation, "referenceInformation");
    this.referenceInformation = referenceInformation;
  }

  @Override
  public IsdaIndexRecoveryRateId.Meta metaBean() {
    return IsdaIndexRecoveryRateId.Meta.INSTANCE;
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
   * Gets the information that identifies the single-name.
   * @return the value of the property, not null
   */
  public IndexReferenceInformation getReferenceInformation() {
    return referenceInformation;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      IsdaIndexRecoveryRateId other = (IsdaIndexRecoveryRateId) obj;
      return JodaBeanUtils.equal(referenceInformation, other.referenceInformation);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(referenceInformation);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("IsdaIndexRecoveryRateId{");
    buf.append("referenceInformation").append('=').append(JodaBeanUtils.toString(referenceInformation));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code IsdaIndexRecoveryRateId}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code referenceInformation} property.
     */
    private final MetaProperty<IndexReferenceInformation> referenceInformation = DirectMetaProperty.ofImmutable(
        this, "referenceInformation", IsdaIndexRecoveryRateId.class, IndexReferenceInformation.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "referenceInformation");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -2117930783:  // referenceInformation
          return referenceInformation;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends IsdaIndexRecoveryRateId> builder() {
      return new IsdaIndexRecoveryRateId.Builder();
    }

    @Override
    public Class<? extends IsdaIndexRecoveryRateId> beanType() {
      return IsdaIndexRecoveryRateId.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code referenceInformation} property.
     * @return the meta-property, not null
     */
    public MetaProperty<IndexReferenceInformation> referenceInformation() {
      return referenceInformation;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -2117930783:  // referenceInformation
          return ((IsdaIndexRecoveryRateId) bean).getReferenceInformation();
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
   * The bean-builder for {@code IsdaIndexRecoveryRateId}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<IsdaIndexRecoveryRateId> {

    private IndexReferenceInformation referenceInformation;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -2117930783:  // referenceInformation
          return referenceInformation;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -2117930783:  // referenceInformation
          this.referenceInformation = (IndexReferenceInformation) newValue;
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
    public IsdaIndexRecoveryRateId build() {
      return new IsdaIndexRecoveryRateId(
          referenceInformation);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("IsdaIndexRecoveryRateId.Builder{");
      buf.append("referenceInformation").append('=').append(JodaBeanUtils.toString(referenceInformation));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
