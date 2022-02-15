/*
 * Designed and developed by 2021 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.koala.bcsd.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class NavScreen(val route: String) {

    object Keyword : NavScreen("Keyword")

    object KeywordDetails : NavScreen("KeywordDetails") {

        const val routeWithArgument: String = "KeywordDetails/{KeywordId}"

        const val argument0: String = "KeywordId"
    }

    object KeywordAdd : NavScreen("KeywordAdd")

    object KeywordAddInput : NavScreen("KeywordAddInput")

    object KeywordSiteAddInput : NavScreen("KeywordTargetInput")

    object HistoryDetails : NavScreen("HistoryDetail") {

        const val routeWithArgument: String = "HistoryDetails/{HistoryId}"

        const val argument0: String = "HistoryId"
    }

    object ChatRoomDetails : NavScreen("ChatRoomDetails") {

        const val routeWithArgument: String = "ChatRoomDetails/{ChatRoomId}"

        const val argument0: String = "ChatRoomId"
    }

    object SettingDetails : NavScreen("SettingDetails") {

        const val routeWithArgument: String = "SettingDetails/{SettingId}"

        const val argument0: String = "SettingId"
    }
}