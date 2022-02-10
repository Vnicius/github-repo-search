package io.github.vnicius.githubreposearch.ui.common.custombottomnavigation

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.core.os.bundleOf
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.core.view.children
import androidx.core.view.forEachIndexed
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.vnicius.githubreposearch.R

/**
 * Created by Vinícius Veríssimo on 25/05/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val menuBuilder: CustomBottomNavigationMenuBuilder by lazy {
        CustomBottomNavigationMenuBuilder(context)
    }
    private val menuPresenter: CustomBottomNavigationMenuPresenter by lazy {
        CustomBottomNavigationMenuPresenter(context)
    }
    private var currentPosition: Int = 0

    @IdRes
    private var selectedItemId: Int = 0
        set(value) {
            field = value
            onSelectedItemIdChanged(value)
        }
    private var itemIconColorTint: ColorStateList? = null
    private var itemTextColor: ColorStateList? = null
    private var isLabelVisible: Boolean = true

    var onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener? =
        null
    var onNavigationItemReselectedListener: BottomNavigationView.OnNavigationItemReselectedListener? =
        null

    init {
        setupPresenterCallBack()
        setupAttrs(attrs)
    }

    private fun setupAttrs(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomBottomNavigationView, 0, 0)
            .apply {
                setItemIconColorList(getColorStateList(R.styleable.CustomBottomNavigationView_itemIconTint))
                setItemTextColorList(getColorStateList(R.styleable.CustomBottomNavigationView_itemTextColor))
                setLabelVisibility(
                    getBoolean(
                        R.styleable.CustomBottomNavigationView_isLabelVisible,
                        true
                    )
                )
                getResourceId(R.styleable.CustomBottomNavigationView_menu, 0).takeIf { it != 0 }
                    ?.let {
                        inflateMenu(it)
                    }
            }
    }

    private fun setupPresenterCallBack() {
        menuPresenter.onItemSelectedCallback =
            object : CustomBottomNavigationMenuPresenter.OnItemSelectedCallback {
                override fun onItemSelected(menuItem: MenuItem) {
                    if (selectedItemId == menuItem.itemId) {
                        onNavigationItemReselectedListener?.onNavigationItemReselected(menuItem)
                    } else {
                        onNavigationItemSelectedListener?.onNavigationItemSelected(menuItem)
                    }
                    selectedItemId = menuItem.itemId
                }
            }
    }

    private fun selectItemId(@IdRes menuItemId: Int) {
        menuBuilder.menu?.findItem(menuItemId)?.let {
            menuPresenter.setItemSelected(it)
        }
    }

    private fun setupMenuPresenterWithCurrentMenu() {
        menuBuilder.menu?.let { menu ->
            menuPresenter.apply {
                setupWithMenu(menu)
                setItemIconColor(itemIconColorTint)
                setItemTextColor(itemTextColor)
                setLabelVisibility(isLabelVisible)
            }

            if (!children.contains(menuPresenter)) {
                addView(
                    menuPresenter,
                    LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                )
            }
            setInitialItemSelection()
        }
    }

    private fun onSelectedItemIdChanged(@IdRes menuItemId: Int) {
        menuBuilder.menu?.forEachIndexed { index, item ->
            if (item.itemId == menuItemId) {
                currentPosition = index
            }
        }
    }

    private fun setInitialItemSelection() {
        val menu = menuBuilder.menu ?: return
        val initialPosition = currentPosition.takeIf { it < menu.size() } ?: 0
        val menuItem = menu.getItem(initialPosition)

        selectItemId(menuItem.itemId)
        onNavigationItemSelectedListener?.onNavigationItemSelected(menuItem)
    }

    @IdRes
    fun getSelectedItemId(): Int = selectedItemId

    fun inflateMenu(@MenuRes menuRes: Int) {
        menuBuilder.inflateMenu(menuRes)
        setupMenuPresenterWithCurrentMenu()
    }

    fun setItemIconColorList(color: ColorStateList?) {
        itemIconColorTint = color
        menuPresenter.setItemIconColor(color)
    }

    fun setItemTextColorList(color: ColorStateList?) {
        itemTextColor = color
        menuPresenter.setItemTextColor(color)
    }

    fun setLabelVisibility(isVisible: Boolean) {
        isLabelVisible = isVisible
        isLabelVisible = isVisible
        menuPresenter.setLabelVisibility(isVisible)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return bundleOf(
            SELECTED_ITEM_POSITION_KEY to currentPosition,
            PARENT_STATE_KEY to super.onSaveInstanceState()
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as? Bundle

        bundle?.getInt(SELECTED_ITEM_POSITION_KEY, 0)?.takeIf { it >= 0 }?.let {
            currentPosition = it
        }

        super.onRestoreInstanceState(bundle?.getParcelable(PARENT_STATE_KEY) ?: state)
    }

    // region NavigationWithNavigationController

    fun setupWithNavController(
        navGraphIds: List<Int>,
        fragmentManager: FragmentManager,
        containerId: Int,
        intent: Intent
    ): LiveData<NavController> {

        // Map of tags
        val graphIdToTagMap = SparseArray<String>()
        // Result. Mutable live data with the selected controlled
        val selectedNavController = MutableLiveData<NavController>()

        var firstFragmentGraphId = 0

        // First create a NavHostFragment for each NavGraph ID
        navGraphIds.forEachIndexed { index, navGraphId ->
            val fragmentTag = getFragmentTag(index)

            // Find or create the Navigation host fragment
            val navHostFragment = obtainNavHostFragment(
                fragmentManager,
                fragmentTag,
                navGraphId,
                containerId
            )

            // Obtain its id
            val graphId = navHostFragment.navController.graph.id

            if (index == 0) {
                firstFragmentGraphId = graphId
            }

            // Save to the map
            graphIdToTagMap[graphId] = fragmentTag

            // Attach or detach nav host fragment depending on whether it's the selected item.
            if (this.selectedItemId == graphId) {
                // Update livedata with the selected graph
                selectedNavController.value = navHostFragment.navController
                attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
            } else {
                detachNavHostFragment(fragmentManager, navHostFragment)
            }
        }

        // Now connect selecting an item with swapping Fragments
        var selectedItemTag = graphIdToTagMap[this.selectedItemId]
        val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
        val navigationStack = mutableListOf<Int>()

        fun navigateToItemId(itemId: Int) {
            val newlySelectedItemTag = graphIdToTagMap[itemId] ?: return
            // Pop everything above the first fragment (the "fixed start destination")
            fragmentManager.popBackStack(
                firstFragmentTag,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            val selectedFragment =
                fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment

            selectedItemTag = newlySelectedItemTag
            selectedNavController.value = selectedFragment.navController
            // Exclude the first fragment tag because it's always in the back stack.
            if (firstFragmentTag != newlySelectedItemTag ||
                (newlySelectedItemTag == firstFragmentTag && navigationStack.isNotEmpty())
            ) {
                // Commit a transaction that cleans the back stack and adds the first fragment
                // to it, creating the fixed started destination.
                fragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.nav_default_enter_anim,
                        R.anim.nav_default_exit_anim,
                        R.anim.nav_default_pop_enter_anim,
                        R.anim.nav_default_pop_exit_anim
                    )
                    .apply {
                        // Detach all other Fragments
                        graphIdToTagMap.forEach { _, fragmentTagIter ->
                            if (fragmentTagIter != newlySelectedItemTag) {
                                detach(
                                    fragmentManager.findFragmentByTag(
                                        firstFragmentTag
                                    )!!
                                )
                            }
                        }
                    }
                    .attach(selectedFragment)
                    .setPrimaryNavigationFragment(selectedFragment)
                    .addToBackStack(firstFragmentTag)
                    .setReorderingAllowed(true)
                    .commit()
            }
        }

        fun addToNavigationStack(@IdRes itemId: Int) {
            if (navigationStack.lastOrNull() != itemId) {
                if (itemId == firstFragmentGraphId && navigationStack.isNotEmpty()) {
                    navigationStack.add(itemId)
                } else if (itemId != firstFragmentGraphId) {
                    navigationStack.add(itemId)
                }
            }
        }

        // When a navigation item is selected
        onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                // Don't do anything if the state is state has already been saved.
                if (fragmentManager.isStateSaved) {
                    false
                } else {
                    val newlySelectedItemTag = graphIdToTagMap[item.itemId]
                        ?: return@OnNavigationItemSelectedListener false
                    if (selectedItemTag != newlySelectedItemTag) {
                        addToNavigationStack(item.itemId)
                        navigateToItemId(item.itemId)
                        true
                    } else {
                        false
                    }
                }
            }

        // Optional: on item reselected, pop back stack to the destination of the graph
        setupItemReselected(graphIdToTagMap, fragmentManager)

        // Handle deep link
        setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

        // Finally, ensure that we update our BottomNavigationView when the back stack changes
        fragmentManager.addOnBackStackChangedListener {
            if (fragmentManager.backStackEntryCount == 0) {
                if (navigationStack.isNotEmpty()) {
                    navigationStack.removeLastOrNull()

                    val nextItemId = if (navigationStack.isEmpty()) {
                        firstFragmentGraphId
                    } else {
                        navigationStack.lastOrNull()
                    }

                    nextItemId?.let(::selectItemId)
                } else {
                    selectItemId(firstFragmentGraphId)
                }
            }

            // Reset the graph if the currentDestination is not valid (happens when the back
            // stack is popped after using the back button).
            selectedNavController.value?.let { controller ->
                if (controller.currentDestination == null) {
                    controller.navigate(controller.graph.id)
                }
            }
        }

        return selectedNavController
    }

    private fun setupDeepLinks(
        navGraphIds: List<Int>,
        fragmentManager: FragmentManager,
        containerId: Int,
        intent: Intent
    ) {
        navGraphIds.forEachIndexed { index, navGraphId ->
            val fragmentTag = getFragmentTag(index)

            // Find or create the Navigation host fragment
            val navHostFragment = obtainNavHostFragment(
                fragmentManager,
                fragmentTag,
                navGraphId,
                containerId
            )
            // Handle Intent
            if (navHostFragment.navController.handleDeepLink(intent) &&
                selectedItemId != navHostFragment.navController.graph.id
            ) {
                selectItemId(navHostFragment.navController.graph.id)
            }
        }
    }

    private fun setupItemReselected(
        graphIdToTagMap: SparseArray<String>,
        fragmentManager: FragmentManager
    ) {
        onNavigationItemReselectedListener =
            BottomNavigationView.OnNavigationItemReselectedListener { item ->
                val newlySelectedItemTag =
                    graphIdToTagMap[item.itemId] ?: return@OnNavigationItemReselectedListener
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                    as NavHostFragment
                val navController = selectedFragment.navController
                // Pop the back stack to the start destination of the current navController graph
                navController.popBackStack(
                    navController.graph.startDestination, false
                )
            }
    }

    private fun getFragmentTag(index: Int) = "bottomNavigation#$index"

    private fun detachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment
    ) {
        fragmentManager.beginTransaction()
            .detach(navHostFragment)
            .commitNow()
    }

    private fun attachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment,
        isPrimaryNavFragment: Boolean
    ) {
        fragmentManager.beginTransaction()
            .attach(navHostFragment)
            .apply {
                if (isPrimaryNavFragment) {
                    setPrimaryNavigationFragment(navHostFragment)
                }
            }
            .commitNow()
    }

    private fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        navGraphId: Int,
        containerId: Int
    ): NavHostFragment {
        // If the Nav Host fragment exists, return it
        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
        existingFragment?.let { return it }

        // Otherwise, create it and return it.
        val navHostFragment = NavHostFragment.create(navGraphId)
        fragmentManager.beginTransaction()
            .add(containerId, navHostFragment, fragmentTag)
            .commitNow()
        return navHostFragment
    }

    private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
        val backStackCount = backStackEntryCount
        for (index in 0 until backStackCount) {
            if (getBackStackEntryAt(index).name == backStackName) {
                return true
            }
        }
        return false
    }

    // endregion

    companion object {
        private const val PARENT_STATE_KEY = "PARENT_STATE_KEY"
        private const val SELECTED_ITEM_POSITION_KEY = "SELECTED_ITEM_POSITION_KEY"
    }
}
