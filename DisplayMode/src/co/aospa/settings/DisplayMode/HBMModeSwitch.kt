/*
* Copyright (C) Yet Another AOSP Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package co.aospa.settings.DisplayMode.ModeSwitch

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceManager

import co.aospa.settings.DisplayMode.Utils

class HBMModeSwitch: OnPreferenceChangeListener() {

    override fun onPreferenceChange(preference: Preference, newValue: Object): Boolean {
        val enabled: Boolean = newValue as Boolean
        Utils.writeValue(getFile(), if (enabled) "5" else "0")
        return true;
    }

    companion object {
        fun getFile() : String? {
            if (Utils.fileWritable(FILE)) {
                return FILE
            }
            return null
        }

        fun isSupported() : Boolean = Utils.fileWritable(getFile())

        fun isCurrentlyEnabled() : Boolean = Utils.getFileValueAsBoolean(getFile(), false)

        private val FILE = "/sys/devices/platform/soc/ae00000.qcom,mdss_mdp/drm/card0/card0-DSI-1/hbm"
     }
}
