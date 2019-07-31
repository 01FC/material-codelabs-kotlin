package com.google.codelabs.mdc.kotlin.shrine

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import com.google.codelabs.mdc.kotlin.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter
import kotlinx.android.synthetic.main.shr_product_grid_fragment.view.*

class ProductGridFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false)

        // set toolbar as the actionbar on this activity
        (activity as AppCompatActivity).setSupportActionBar(view.app_bar)
        view.app_bar.setNavigationOnClickListener(
                NavigationIconClickListener(
                        activity!!,
                        view.product_grid,
                        AccelerateDecelerateInterpolator(),
                        ContextCompat.getDrawable(context!!, R.drawable.shr_branded_menu),  // Menu open icon
                        ContextCompat.getDrawable(context!!, R.drawable.shr_close_menu)     // Menu close icon
                )
        )
        // Set up the RecyclerView
        view.recycler_view.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }
        view.recycler_view.layoutManager = gridLayoutManager
        val adapter = StaggeredProductCardRecyclerViewAdapter(
                ProductEntry.initProductEntryList(resources)
        )

        view.recycler_view.adapter = adapter
        val largePadding = resources.getDimension(R.dimen.shr_product_grid_spacing)
        val smallPadding = resources.getDimension(R.dimen.shr_product_grid_spacing_small)
        view.recycler_view.addItemDecoration(ProductGridItemDecoration(largePadding.toInt(), smallPadding.toInt()))
        // Set cut corner background for API 23+
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.product_grid.background = context?.getDrawable(R.drawable.shr_product_grid_background_shape)
        //}
        return view
    }

    /** Set menu layout **/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /** Set menu existence true **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}
