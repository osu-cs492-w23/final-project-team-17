package com.example.steamapp.data

import com.example.steamapp.api.App
import com.example.steamapp.api.AppList

/**
 * A singleton class that stores the names and app ids of all apps from the Steam Web API.
 * This is highly inefficient, but it is the only way to search apps on Steam due to its
 * API's design.
 */
class AppListStorage(private val appList: AppList) {

    /**
     * Returns all apps where their name contains the substring from the search parameter.
     *
     * @param search Substring to search each app name for
     * @return List of Apps whose name contains the substring
     */
    fun filterListByName(search: String): List<App> {
        return appList.apps.filter { it.name.contains(search) }
    }

    /**
     * Returns the name of the app with the corresponding id.
     *
     * @param id Id of the app
     * @return App's name as string, null if not found
     */
    fun getAppNameById(id: UInt): String? {
        return appList.apps.find { it.appid == id }?.name
    }

    /**
     * Returns an app id corresponding to the name parameter.
     *
     * Search works by finding the list of all apps whose name contains the name parameter as a
     * substring and returns the id of the first result.
     *
     * @param name Substring to search for
     * @return App id of the searched name or null if not found.
     */
    fun getAppIdByFuzzyName(name: String): UInt? {
        return try {
            this.filterListByName(name).first().appid
        } catch (e: NoSuchElementException) {
            null
        }
    }

    /**
     * Returns the app id corresponding to the name parameter.
     *
     * The name parameter must perfectly match the one contained in the app list otherwise this
     * will return null. This version exists because its presumably faster than the filter method.
     *
     * @param name The exact App name to search for
     * @return App id of the searched name or null if not found.
     */
    fun getAppIdByExactName(name: String): UInt? {
        return appList.apps.find { it.name == name }?.appid
    }
}