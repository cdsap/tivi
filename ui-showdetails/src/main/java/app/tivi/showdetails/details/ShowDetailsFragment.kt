/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.tivi.showdetails.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import app.tivi.TiviFragment
import app.tivi.common.compose.observeWindowInsets
import app.tivi.extensions.scheduleStartPostponedTransitions
import app.tivi.showdetails.details.view.ShowDetailsTextCreator
import app.tivi.showdetails.details.view.composeShowDetails
import app.tivi.util.TiviDateFormatter
import com.airbnb.mvrx.fragmentViewModel
import javax.inject.Inject

class ShowDetailsFragment : TiviFragment(), ShowDetailsFragmentViewModel.FactoryProvider {
    private val viewModel: ShowDetailsFragmentViewModel by fragmentViewModel()

    @Inject internal lateinit var showDetailsViewModelFactory: ShowDetailsFragmentViewModel.Factory
    @Inject internal lateinit var textCreator: ShowDetailsTextCreator
    @Inject internal lateinit var tiviDateFormatter: TiviDateFormatter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            composeShowDetails(
                viewLifecycleOwner,
                viewModel.observeAsLiveData(),
                observeWindowInsets(),
                viewModel::submitAction,
                tiviDateFormatter,
                textCreator
            )
        }
    }

    override fun onStart() {
        super.onStart()
        // TODO move this once we know how to handle transitions in Compose
        scheduleStartPostponedTransitions()
    }

    override fun invalidate() = Unit

    override fun provideFactory(): ShowDetailsFragmentViewModel.Factory = showDetailsViewModelFactory
}
