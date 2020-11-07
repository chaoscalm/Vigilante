package com.crazylegend.vigilante.power.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.crazylegend.navigation.navigateSafe
import com.crazylegend.recyclerview.clickListeners.forItemClickListener
import com.crazylegend.viewbinding.viewBinding
import com.crazylegend.vigilante.R
import com.crazylegend.vigilante.abstracts.AbstractFragment
import com.crazylegend.vigilante.contracts.LoadingDBsInFragments
import com.crazylegend.vigilante.databinding.LayoutRecyclerBinding
import com.crazylegend.vigilante.di.providers.DatabaseLoadingProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by crazy on 11/7/20 to long live and prosper !
 */
@AndroidEntryPoint
class PowerFragment : AbstractFragment<LayoutRecyclerBinding>(R.layout.layout_recycler), LoadingDBsInFragments {

    override val binding by viewBinding(LayoutRecyclerBinding::bind)

    @Inject
    override lateinit var databaseLoadingProvider: DatabaseLoadingProvider

    private val adapter by lazy {
        adapterProvider.powerAdapter
    }

    private val powerVM by viewModels<PowerVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseLoadingProvider.provideListState(powerVM.powerHistory, binding.recycler, binding.noDataViewHolder.noDataView, adapter)
        adapter.forItemClickListener = forItemClickListener { _, item, _ ->
            if (item.isCharging)
                findNavController().navigateSafe(PowerFragmentDirections.destinationPowerDetails(item.id))
        }
    }
}