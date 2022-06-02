/*
* Copyright (C) 2021 Yet Another AOSP Project
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
package co.aospa.settings.DisplayMode

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

import co.aospa.settings.DisplayMode.ModeSwitch.HBMModeSwitch

class HBMModeTileService: TileService () {
    private var mHbmIntent: Intent? = null

   override fun onDestroy() {
        super.onDestroy()
    }

    override fun onTileAdded() {
        super.onTileAdded()
    }

    override fun onTileRemoved() {
        tryStopService()
        super.onTileRemoved()
    }

    override fun onStartListening() {
        super.onStartListening()
        updateState()
    }

    override fun onStopListening() {
        super.onStopListening()
    }

    override fun onClick() {
        super.onClick()
        val enabled: Boolean = HBMModeSwitch.isCurrentlyEnabled()
        // NOTE: reverse logic, enabled reflects the state before press
        Utils.writeValue(HBMModeSwitch.getFile(), if (enabled) "0" else "5")
        if (!enabled) {
            mHbmIntent = Intent(this, co.aospa.settings.DisplayMode.HBMModeService::class.java)
            this.startService(mHbmIntent)
        }
        updateState()
    }

    private fun updateState() {
        val enabled: Boolean = HBMModeSwitch.isCurrentlyEnabled()
        if (!enabled) tryStopService()
        getQsTile().setState(if (enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        getQsTile().updateTile()
    }

    private fun tryStopService() {
        this.stopService(mHbmIntent!!)
        mHbmIntent = null
    }
}
