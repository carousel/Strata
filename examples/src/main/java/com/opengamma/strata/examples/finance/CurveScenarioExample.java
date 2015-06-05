/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.strata.examples.finance;

import static com.opengamma.strata.basics.date.BusinessDayConventions.MODIFIED_FOLLOWING;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.opengamma.strata.basics.PayReceive;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.currency.FxRate;
import com.opengamma.strata.basics.date.BusinessDayAdjustment;
import com.opengamma.strata.basics.date.DayCounts;
import com.opengamma.strata.basics.date.DaysAdjustment;
import com.opengamma.strata.basics.date.HolidayCalendars;
import com.opengamma.strata.basics.index.IborIndices;
import com.opengamma.strata.basics.market.FxRateId;
import com.opengamma.strata.basics.schedule.Frequency;
import com.opengamma.strata.basics.schedule.PeriodicSchedule;
import com.opengamma.strata.collect.id.StandardId;
import com.opengamma.strata.engine.CalculationEngine;
import com.opengamma.strata.engine.CalculationRules;
import com.opengamma.strata.engine.Column;
import com.opengamma.strata.engine.calculations.Results;
import com.opengamma.strata.engine.calculations.function.result.CurrencyAmountList;
import com.opengamma.strata.engine.config.Measure;
import com.opengamma.strata.engine.config.ReportingRules;
import com.opengamma.strata.engine.marketdata.BaseMarketData;
import com.opengamma.strata.engine.marketdata.scenarios.Perturbation;
import com.opengamma.strata.engine.marketdata.scenarios.PerturbationMapping;
import com.opengamma.strata.engine.marketdata.scenarios.ScenarioDefinition;
import com.opengamma.strata.examples.engine.ExampleEngine;
import com.opengamma.strata.examples.marketdata.ExampleMarketData;
import com.opengamma.strata.finance.Trade;
import com.opengamma.strata.finance.TradeInfo;
import com.opengamma.strata.finance.rate.swap.FixedRateCalculation;
import com.opengamma.strata.finance.rate.swap.IborRateCalculation;
import com.opengamma.strata.finance.rate.swap.NotionalSchedule;
import com.opengamma.strata.finance.rate.swap.PaymentSchedule;
import com.opengamma.strata.finance.rate.swap.RateCalculationSwapLeg;
import com.opengamma.strata.finance.rate.swap.Swap;
import com.opengamma.strata.finance.rate.swap.SwapLeg;
import com.opengamma.strata.finance.rate.swap.SwapTrade;
import com.opengamma.strata.function.OpenGammaPricingRules;
import com.opengamma.strata.function.marketdata.scenarios.curves.AnyCurveFilter;
import com.opengamma.strata.function.marketdata.scenarios.curves.CurveParallelShift;
import com.opengamma.strata.market.curve.Curve;

/**
 * Example to illustrate using the scenario framework to apply shifts to calibrated curves.
 * <p>
 * Two scenarios are run:
 * <ul>
 *   <li>A base scenario with no perturbations applied to the market data</li>
 *   <li>A scenario with a 1 basis point shift applied to all curves</li>
 * </ul>
 * Present value and PV01 are calculated for a single swap. The present value from the second scenario
 * is compared to the sum of the present value and PV01 from the base scenario.
 * <p>
 * This makes use of the example engine which sources the required market data from
 * JSON resources.
 */
public class CurveScenarioExample {

  private static final double ONE_BP = 1e-4;

  /**
   * Runs the example, pricing the instruments, producing the output as an ASCII table.
   * 
   * @param args  ignored
   */
  public static void main(String[] args) {
    // the trade that will have measures calculated
    List<Trade> trades = ImmutableList.of(createVanillaFixedVsLibor3mSwap());

    // the columns, specifying the measures to be calculated
    List<Column> columns = ImmutableList.of(
        Column.of(Measure.PRESENT_VALUE),
        Column.of(Measure.PV01));

    // the complete set of rules for calculating measures
    CalculationRules rules = CalculationRules.builder()
        .pricingRules(OpenGammaPricingRules.standard())
        .marketDataRules(ExampleMarketData.rules())
        .reportingRules(ReportingRules.fixedCurrency(Currency.USD))
        .build();

    // Two perturbations affecting curves
    List<Perturbation<Curve>> curvePerturbations = ImmutableList.of(
        Perturbation.none(),                  // No shift for the base scenario
        CurveParallelShift.absolute(ONE_BP)); // 1bp absolute shift to calibrated curves

    // A mapping for perturbing curves. The filter matches any curve.
    PerturbationMapping<Curve> mapping = PerturbationMapping.of(
        Curve.class,
        AnyCurveFilter.INSTANCE,
        curvePerturbations);

    // Create a scenario definition containing the single mapping.
    // This creates two scenarios, one for each perturbation in  the mapping
    ScenarioDefinition scenarioDefinition = ScenarioDefinition.ofMappings(mapping);

    // Use an empty snapshot of market data, indicating only the valuation date.
    // The engine will attempt to source the data for us, which the example engine is
    // configured to load from JSON resources. We could alternatively populate the snapshot
    // with some or all of the required market data here.
    LocalDate valuationDate = LocalDate.of(2014, 1, 22);
    // TODO The rate is for automatic conversion to the reporting currency. Where should it come from?
    BaseMarketData baseMarketData = BaseMarketData.builder(valuationDate)
        .addValue(FxRateId.of(Currency.GBP, Currency.USD), FxRate.of(Currency.GBP, Currency.USD, 1.61))
        .build();

    // create the engine and calculate the results
    CalculationEngine engine = ExampleEngine.create();
    Results results = engine.calculate(trades, columns, rules, baseMarketData, scenarioDefinition);

    // The results are lists of currency amounts containing one value for each scenario
    CurrencyAmountList pvList = (CurrencyAmountList) results.get(0, 0).getValue();
    CurrencyAmountList pv01List = (CurrencyAmountList) results.get(0, 1).getValue();

    double pvBase = pvList.get(0).getAmount();
    double pvShifted = pvList.get(1).getAmount();
    // The swap PV01 function returns a value for a shift of 1, not 1 basis point, so need to scale the result
    double pv01Base = pv01List.get(0).getAmount() * ONE_BP;
    NumberFormat numberFormat = new DecimalFormat("###,##0.00");

    // TODO Replace these with a report once the reporting framework supports scenarios
    System.out.println("Base PV        = " + numberFormat.format(pvBase));
    System.out.println("Base PV01      = " + numberFormat.format(pv01Base));
    System.out.println("Base PV + PV01 = " + numberFormat.format(pvBase + pv01Base));
    System.out.println("Shifted PV     = " + numberFormat.format(pvShifted));
    System.out.println("Difference     = " + numberFormat.format(pvShifted - pvBase - pv01Base));
  }

  //-----------------------------------------------------------------------
  // create a vanilla fixed vs libor 3m swap
  private static Trade createVanillaFixedVsLibor3mSwap() {
    NotionalSchedule notional = NotionalSchedule.of(Currency.USD, 100_000_000);

    SwapLeg payLeg = RateCalculationSwapLeg.builder()
        .payReceive(PayReceive.PAY)
        .accrualSchedule(PeriodicSchedule.builder()
            .startDate(LocalDate.of(2014, 9, 12))
            .endDate(LocalDate.of(2021, 9, 12))
            .frequency(Frequency.P6M)
            .businessDayAdjustment(BusinessDayAdjustment.of(MODIFIED_FOLLOWING, HolidayCalendars.USNY))
            .build())
        .paymentSchedule(PaymentSchedule.builder()
            .paymentFrequency(Frequency.P6M)
            .paymentDateOffset(DaysAdjustment.NONE)
            .build())
        .notionalSchedule(notional)
        .calculation(FixedRateCalculation.of(0.015, DayCounts.THIRTY_U_360))
        .build();

    SwapLeg receiveLeg = RateCalculationSwapLeg.builder()
        .payReceive(PayReceive.RECEIVE)
        .accrualSchedule(PeriodicSchedule.builder()
            .startDate(LocalDate.of(2014, 9, 12))
            .endDate(LocalDate.of(2021, 9, 12))
            .frequency(Frequency.P3M)
            .businessDayAdjustment(BusinessDayAdjustment.of(MODIFIED_FOLLOWING, HolidayCalendars.USNY))
            .build())
        .paymentSchedule(PaymentSchedule.builder()
            .paymentFrequency(Frequency.P3M)
            .paymentDateOffset(DaysAdjustment.NONE)
            .build())
        .notionalSchedule(notional)
        .calculation(IborRateCalculation.of(IborIndices.USD_LIBOR_3M))
        .build();

    return SwapTrade.builder()
        .product(Swap.of(payLeg, receiveLeg))
        .tradeInfo(TradeInfo.builder()
            .attributes(ImmutableMap.of("description", "Fixed vs Libor 3m"))
            .counterparty(StandardId.of("example", "A"))
            .settlementDate(LocalDate.of(2014, 9, 12))
            .build())
        .build();
  }
}
