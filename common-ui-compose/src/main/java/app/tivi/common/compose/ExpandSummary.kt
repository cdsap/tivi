/*
 * Copyright 2020 Google LLC
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

package app.tivi.common.compose

import androidx.compose.Composable
import androidx.compose.state
import androidx.compose.stateFor
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.material.EmphasisAmbient
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ProvideEmphasis
import androidx.ui.material.ripple.ripple
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextOverflow

@Composable
fun ExpandingSummary(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    collapsedMaxLines: Int = 4,
    expandedMaxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier.None
) {
    var canExpand by stateFor(text) { true }

    Box(modifier = Modifier.ripple(bounded = true, enabled = canExpand)) {
        var expanded by state { false }

        Clickable(onClick = { expanded = !expanded }, enabled = canExpand) {
            ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
                Text(
                    text = text,
                    style = textStyle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (expanded) expandedMaxLines else collapsedMaxLines,
                    modifier = modifier
                ) {
                    if (!expanded) {
                        // canExpand = it.hasVisualOverflow
                    }
                }
            }
        }
    }
}
