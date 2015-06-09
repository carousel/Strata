/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * <p>
 * Please see distribution for license.
 */
package com.opengamma.strata.finance.credit.fee;

import org.joda.beans.*;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Upfront fee agreed upon between buyer and seller.
 * <p>
 * Specifies a single fixed amount that is payable by the buyer to the seller on the fixed rate payer payment date.
 * The fixed amount to be paid is specified in terms of a known currency amount.
 */
@BeanDefinition
public final class SinglePayment
        implements ImmutableBean, Serializable {

    /**
     * A fixed payment amount. ISDA 2003 Term: Fixed Amount
     */
    @PropertyDefinition(validate = "notNull")
    final double fixedAmount;

    /**
     * The adjusted payment date. This date should already be adjusted for any applicable business day convention.
     * This component is not intended for use in trade confirmation but may be specified to allow the fee structure
     * to also serve as a cashflow type component.
     */
    @PropertyDefinition(validate = "notNull")
    final LocalDate paymentDate;

    public static SinglePayment of(double fixedAmount, LocalDate paymentDate) {
        return new SinglePayment(
                fixedAmount,
                paymentDate
        );
    }
    //------------------------- AUTOGENERATED START -------------------------
    ///CLOVER:OFF

    /**
     * The meta-bean for {@code SinglePayment}.
     *
     * @return the meta-bean, not null
     */
    public static SinglePayment.Meta meta() {
        return SinglePayment.Meta.INSTANCE;
    }

    static {
        JodaBeanUtils.registerMetaBean(SinglePayment.Meta.INSTANCE);
    }

    /**
     * The serialization version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Returns a builder used to create an instance of the bean.
     *
     * @return the builder, not null
     */
    public static SinglePayment.Builder builder() {
        return new SinglePayment.Builder();
    }

    private SinglePayment(
            double fixedAmount,
            LocalDate paymentDate) {
        JodaBeanUtils.notNull(fixedAmount, "fixedAmount");
        JodaBeanUtils.notNull(paymentDate, "paymentDate");
        this.fixedAmount = fixedAmount;
        this.paymentDate = paymentDate;
    }

    @Override
    public SinglePayment.Meta metaBean() {
        return SinglePayment.Meta.INSTANCE;
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
     * Gets a fixed payment amount. ISDA 2003 Term: Fixed Amount
     *
     * @return the value of the property, not null
     */
    public double getFixedAmount() {
        return fixedAmount;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the adjusted payment date. This date should already be adjusted for any applicable business day convention.
     * This component is not intended for use in trade confirmation but may be specified to allow the fee structure
     * to also serve as a cashflow type component.
     *
     * @return the value of the property, not null
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    //-----------------------------------------------------------------------

    /**
     * Returns a builder that allows this bean to be mutated.
     *
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
            SinglePayment other = (SinglePayment) obj;
            return JodaBeanUtils.equal(getFixedAmount(), other.getFixedAmount()) &&
                    JodaBeanUtils.equal(getPaymentDate(), other.getPaymentDate());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getFixedAmount());
        hash = hash * 31 + JodaBeanUtils.hashCode(getPaymentDate());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(96);
        buf.append("SinglePayment{");
        buf.append("fixedAmount").append('=').append(getFixedAmount()).append(',').append(' ');
        buf.append("paymentDate").append('=').append(JodaBeanUtils.toString(getPaymentDate()));
        buf.append('}');
        return buf.toString();
    }

    //-----------------------------------------------------------------------

    /**
     * The meta-bean for {@code SinglePayment}.
     */
    public static final class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code fixedAmount} property.
         */
        private final MetaProperty<Double> fixedAmount = DirectMetaProperty.ofImmutable(
                this, "fixedAmount", SinglePayment.class, Double.TYPE);
        /**
         * The meta-property for the {@code paymentDate} property.
         */
        private final MetaProperty<LocalDate> paymentDate = DirectMetaProperty.ofImmutable(
                this, "paymentDate", SinglePayment.class, LocalDate.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "fixedAmount",
                "paymentDate");

        /**
         * Restricted constructor.
         */
        private Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 540523756:  // fixedAmount
                    return fixedAmount;
                case -1540873516:  // paymentDate
                    return paymentDate;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public SinglePayment.Builder builder() {
            return new SinglePayment.Builder();
        }

        @Override
        public Class<? extends SinglePayment> beanType() {
            return SinglePayment.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------

        /**
         * The meta-property for the {@code fixedAmount} property.
         *
         * @return the meta-property, not null
         */
        public MetaProperty<Double> fixedAmount() {
            return fixedAmount;
        }

        /**
         * The meta-property for the {@code paymentDate} property.
         *
         * @return the meta-property, not null
         */
        public MetaProperty<LocalDate> paymentDate() {
            return paymentDate;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 540523756:  // fixedAmount
                    return ((SinglePayment) bean).getFixedAmount();
                case -1540873516:  // paymentDate
                    return ((SinglePayment) bean).getPaymentDate();
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
     * The bean-builder for {@code SinglePayment}.
     */
    public static final class Builder extends DirectFieldsBeanBuilder<SinglePayment> {

        private double fixedAmount;
        private LocalDate paymentDate;

        /**
         * Restricted constructor.
         */
        private Builder() {
        }

        /**
         * Restricted copy constructor.
         *
         * @param beanToCopy the bean to copy from, not null
         */
        private Builder(SinglePayment beanToCopy) {
            this.fixedAmount = beanToCopy.getFixedAmount();
            this.paymentDate = beanToCopy.getPaymentDate();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case 540523756:  // fixedAmount
                    return fixedAmount;
                case -1540873516:  // paymentDate
                    return paymentDate;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case 540523756:  // fixedAmount
                    this.fixedAmount = (Double) newValue;
                    break;
                case -1540873516:  // paymentDate
                    this.paymentDate = (LocalDate) newValue;
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
        public SinglePayment build() {
            return new SinglePayment(
                    fixedAmount,
                    paymentDate);
        }

        //-----------------------------------------------------------------------

        /**
         * Sets the {@code fixedAmount} property in the builder.
         *
         * @param fixedAmount the new value, not null
         * @return this, for chaining, not null
         */
        public Builder fixedAmount(double fixedAmount) {
            JodaBeanUtils.notNull(fixedAmount, "fixedAmount");
            this.fixedAmount = fixedAmount;
            return this;
        }

        /**
         * Sets the {@code paymentDate} property in the builder.
         *
         * @param paymentDate the new value, not null
         * @return this, for chaining, not null
         */
        public Builder paymentDate(LocalDate paymentDate) {
            JodaBeanUtils.notNull(paymentDate, "paymentDate");
            this.paymentDate = paymentDate;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder(96);
            buf.append("SinglePayment.Builder{");
            buf.append("fixedAmount").append('=').append(JodaBeanUtils.toString(fixedAmount)).append(',').append(' ');
            buf.append("paymentDate").append('=').append(JodaBeanUtils.toString(paymentDate));
            buf.append('}');
            return buf.toString();
        }

    }

    ///CLOVER:ON
    //-------------------------- AUTOGENERATED END --------------------------
}
