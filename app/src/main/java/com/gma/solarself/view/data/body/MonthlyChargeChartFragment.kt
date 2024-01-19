package com.gma.solarself.view.data.body

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentMonthlyChargeChartBinding
import com.gma.solarself.model.MonthlyChargeModel
import com.gma.solarself.utils.currenMonthAndYear
import com.gma.solarself.utils.currentDayAndMonth
import com.gma.solarself.utils.getColorInRGB
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import java.util.Date

class MonthlyChargeChartFragment(
    private val stationId: String
) : PatternFragment<FragmentMonthlyChargeChartBinding, MonthlyChargeViewModel>(
    FragmentMonthlyChargeChartBinding::inflate,
    MonthlyChargeViewModel::class
) {
    private var canLoad = false

    override fun setupViews() {
        viewModel.fetchMonthlySummary(stationId)
    }

    override fun setupObservers() {
        viewModel.referenceDate.observe(requireActivity(), ::setupReferenceDateText)
        viewModel.monthlySummary.observe(requireActivity(), ::setupMonthlyChart)
        viewModel.loading.observe(requireActivity(), ::setupLoading)
    }

    private fun setupLoading(isVisible: Boolean) {
        canLoad = isVisible
        if (isVisible) {
            binding.root.showShimmer(true)
        } else {
            binding.root.stopShimmer()
            binding.root.hideShimmer()
        }
    }

    private fun setupReferenceDateText(referenceDate: Date) {
        /*val title = String.format(
            getString(R.string.period_charge_chart_title),
            initialDate, finalDate
        )*/
        binding.chartTitle.text = referenceDate.currenMonthAndYear()
    }

    private fun setupMonthlyChart(chartModel: MonthlyChargeModel?) {
        if (canLoad) {
            chartModel?.let { monthlyChartModel ->
                val chartComponent = AAChartModel()
                    .chartType(AAChartType.Column)
                    .backgroundColor(getColorInRGB(requireContext(), R.color.primarySurface))
                    .axesTextColor(getColorInRGB(requireContext(), R.color.textColorPrimary))
                    .yAxisTitle(getString(R.string.kwh))
                    .dataLabelsEnabled(true)
                    .categories(monthlyChartModel.monthlyData.map {
                        it.date.currentDayAndMonth()
                    }.toTypedArray())
                    .series(
                        arrayOf(
                            AASeriesElement()
                                .name(getString(R.string.period_charge))
                                .data(monthlyChartModel.monthlyData.map {
                                    it.energy
                                }.toTypedArray())
                                .color(getColorInRGB(requireActivity(), R.color.primaryInverse))
                                .tooltip(
                                    AATooltip()
                                        .pointFormat(getString(R.string.period_charge_chart_tooltip))
                                )
                        )
                    )

                binding.chartView.aa_drawChartWithChartModel(chartComponent)
            }
        }
    }
}