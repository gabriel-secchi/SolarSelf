package com.gma.solarself.view.data.body

import androidx.core.view.isVisible
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import com.gma.infrastructure.model.ConfigDatePeriodModel
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentPeriodChargeChartBinding
import com.gma.solarself.model.PeriodChargeModel
import com.gma.solarself.utils.currentDayAndMonth
import com.gma.solarself.utils.getColorInRGB
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.viewModel.PeriodChargeViewModel

class PeriodChargeChartFragment(
    private val stationId: String
) : PatternFragment<FragmentPeriodChargeChartBinding, PeriodChargeViewModel>(
    FragmentPeriodChargeChartBinding::inflate,
    PeriodChargeViewModel::class
) {
    private var canLoad = false

    override fun setupViews() {
        viewModel.fetchPeriodSummary(stationId)
    }

    override fun setupObservers() {
        viewModel.referencePeriod.observe(viewLifecycleOwner, ::setupReferencePeriodText)
        viewModel.periodCharge.observe(viewLifecycleOwner, ::setupPeriodChartData)
        viewModel.loading.observe(viewLifecycleOwner, ::setupLoading)
        viewModel.noPeriodConfigured.observe(viewLifecycleOwner) { notConfigured ->
            binding.root.isVisible = !notConfigured
        }
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

    private fun setupReferencePeriodText(period: ConfigDatePeriodModel) {
        val initialDate = period.startDate.currentDayAndMonth()
        val finalDate = period.endDate.currentDayAndMonth()
        val title = String.format(
            getString(R.string.period_charge_chart_title),
            initialDate, finalDate
        )
        binding.chartTitle.text = title
    }

    private fun setupPeriodChartData(chartModel: PeriodChargeModel?) {
        if (canLoad) {
            chartModel?.let { periodChartModel ->
                val chartComponent = AAChartModel()
                    .chartType(AAChartType.Column)
                    .backgroundColor(getColorInRGB(requireContext(), R.color.primarySurface))
                    .axesTextColor(getColorInRGB(requireContext(), R.color.textColorPrimary))
                    .yAxisTitle(getString(R.string.kwh))
                    .dataLabelsEnabled(true)
                    .categories(periodChartModel.listEnergy.map {
                        it.date.currentDayAndMonth()
                    }.toTypedArray())
                    .series(
                        arrayOf(
                            AASeriesElement()
                                .name(getString(R.string.period_charge))
                                .data(periodChartModel.listEnergy.map {
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