package me.michalkasza.smartlock

import eu.vranckaert.driver.touch.TouchScreenDriverApplication
import eu.vranckaert.driver.touch.profile.DriverProfile
import eu.vranckaert.driver.touch.profile.WaveshareProfile

class App : TouchScreenDriverApplication() {
    override fun getDriverProfile(): DriverProfile {
        return WaveshareProfile.getInstance(WaveshareProfile.DIMENSION_800_480)
    }
}