/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.surface;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

import com.google.common.base.Preconditions;
import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.market.param.ParameterPerturbation;
import com.opengamma.strata.market.param.UnitParameterSensitivity;

/**
 * A surface based on a single constant value.
 * <p>
 * This class defines a surface in terms of a single parameter, the constant value.
 * When queried, {@link #zValue(double, double)} always returns the constant value.
 * The sensitivity is 1 and the first derivative is 0.
 * <p>
 * The surface has one parameter, the value of the constant.
 */
@BeanDefinition(builderScope = "private")
public final class ConstantSurface
    implements Surface, ImmutableBean, Serializable {

  /**
   * Sensitivity does not vary.
   */
  private static final DoubleArray SENSITIVITY = DoubleArray.of(1d);

  /**
   * The surface metadata.
   * <p>
   * The metadata will have not have parameter metadata.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final SurfaceMetadata metadata;
  /**
   * The single z-value.
   */
  @PropertyDefinition
  private final double zValue;

  //-------------------------------------------------------------------------
  /**
   * Creates a constant surface with a specific value.
   * 
   * @param name  the surface name
   * @param zValue  the constant z-value
   * @return the surface
   */
  public static ConstantSurface of(String name, double zValue) {
    return of(SurfaceName.of(name), zValue);
  }

  /**
   * Creates a constant surface with a specific value.
   * 
   * @param name  the surface name
   * @param zValue  the constant z-value
   * @return the surface
   */
  public static ConstantSurface of(SurfaceName name, double zValue) {
    return new ConstantSurface(DefaultSurfaceMetadata.of(name), zValue);
  }

  /**
   * Creates a constant surface with a specific value.
   * 
   * @param metadata  the surface metadata
   * @param zValue  the constant z-value
   * @return the surface
   */
  public static ConstantSurface of(SurfaceMetadata metadata, double zValue) {
    return new ConstantSurface(metadata, zValue);
  }

  //-------------------------------------------------------------------------
  // ensure standard constructor is invoked
  private Object readResolve() {
    return new ConstantSurface(metadata, zValue);
  }

  //-------------------------------------------------------------------------
  @Override
  public int getParameterCount() {
    return 1;
  }

  @Override
  public double getParameter(int parameterIndex) {
    Preconditions.checkElementIndex(parameterIndex, 1);
    return zValue;
  }

  @Override
  public ConstantSurface withParameter(int parameterIndex, double newValue) {
    Preconditions.checkElementIndex(parameterIndex, 1);
    return new ConstantSurface(metadata, newValue);
  }

  @Override
  public ConstantSurface withPerturbation(ParameterPerturbation perturbation) {
    return new ConstantSurface(metadata, perturbation.perturbParameter(0, zValue, getParameterMetadata(0)));
  }

  //-------------------------------------------------------------------------
  @Override
  public double zValue(double x, double y) {
    return zValue;
  }

  @Override
  public UnitParameterSensitivity zValueParameterSensitivity(double x, double y) {
    return createParameterSensitivity(SENSITIVITY);
  }

  //-------------------------------------------------------------------------
  @Override
  public ConstantSurface withMetadata(SurfaceMetadata metadata) {
    return new ConstantSurface(metadata.withParameterMetadata(null), zValue);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code ConstantSurface}.
   * @return the meta-bean, not null
   */
  public static ConstantSurface.Meta meta() {
    return ConstantSurface.Meta.INSTANCE;
  }

  static {
    MetaBean.register(ConstantSurface.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private ConstantSurface(
      SurfaceMetadata metadata,
      double zValue) {
    JodaBeanUtils.notNull(metadata, "metadata");
    this.metadata = metadata;
    this.zValue = zValue;
  }

  @Override
  public ConstantSurface.Meta metaBean() {
    return ConstantSurface.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the surface metadata.
   * <p>
   * The metadata will have not have parameter metadata.
   * @return the value of the property, not null
   */
  @Override
  public SurfaceMetadata getMetadata() {
    return metadata;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the single z-value.
   * @return the value of the property
   */
  public double getZValue() {
    return zValue;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ConstantSurface other = (ConstantSurface) obj;
      return JodaBeanUtils.equal(metadata, other.metadata) &&
          JodaBeanUtils.equal(zValue, other.zValue);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(metadata);
    hash = hash * 31 + JodaBeanUtils.hashCode(zValue);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("ConstantSurface{");
    buf.append("metadata").append('=').append(JodaBeanUtils.toString(metadata)).append(',').append(' ');
    buf.append("zValue").append('=').append(JodaBeanUtils.toString(zValue));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ConstantSurface}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code metadata} property.
     */
    private final MetaProperty<SurfaceMetadata> metadata = DirectMetaProperty.ofImmutable(
        this, "metadata", ConstantSurface.class, SurfaceMetadata.class);
    /**
     * The meta-property for the {@code zValue} property.
     */
    private final MetaProperty<Double> zValue = DirectMetaProperty.ofImmutable(
        this, "zValue", ConstantSurface.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "metadata",
        "zValue");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -450004177:  // metadata
          return metadata;
        case -719790825:  // zValue
          return zValue;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ConstantSurface> builder() {
      return new ConstantSurface.Builder();
    }

    @Override
    public Class<? extends ConstantSurface> beanType() {
      return ConstantSurface.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code metadata} property.
     * @return the meta-property, not null
     */
    public MetaProperty<SurfaceMetadata> metadata() {
      return metadata;
    }

    /**
     * The meta-property for the {@code zValue} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> zValue() {
      return zValue;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -450004177:  // metadata
          return ((ConstantSurface) bean).getMetadata();
        case -719790825:  // zValue
          return ((ConstantSurface) bean).getZValue();
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
   * The bean-builder for {@code ConstantSurface}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<ConstantSurface> {

    private SurfaceMetadata metadata;
    private double zValue;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -450004177:  // metadata
          return metadata;
        case -719790825:  // zValue
          return zValue;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -450004177:  // metadata
          this.metadata = (SurfaceMetadata) newValue;
          break;
        case -719790825:  // zValue
          this.zValue = (Double) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public ConstantSurface build() {
      return new ConstantSurface(
          metadata,
          zValue);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("ConstantSurface.Builder{");
      buf.append("metadata").append('=').append(JodaBeanUtils.toString(metadata)).append(',').append(' ');
      buf.append("zValue").append('=').append(JodaBeanUtils.toString(zValue));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
