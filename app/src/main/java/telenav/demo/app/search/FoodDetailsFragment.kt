package telenav.demo.app.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.view_entity_details_bottom.*
import telenav.demo.app.R
import telenav.demo.app.databinding.FragmentFoodDetailsBinding
import telenav.demo.app.model.SearchResult
import telenav.demo.app.utils.CategoryAndFiltersUtil

class FoodDetailsFragment : Fragment() {

    private var binding: FragmentFoodDetailsBinding? = null
    private var searchResult: SearchResult? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (searchResult?.address.isNullOrEmpty()) {
            binding?.entityAddress?.text = ""
        } else {
            binding?.entityAddress?.text = searchResult?.address
        }

        if (!searchResult?.hours.isNullOrEmpty()) {
            binding?.entityOpenHours?.text = searchResult?.hours
        }

        if (searchResult?.phoneNo.isNullOrEmpty()) {
            binding?.entityPhoneNumber?.text = ""
            binding?.entityPhoneNumberHeader?.visibility = View.VISIBLE
        } else {
            binding?.entityPhoneNumber?.text = searchResult?.phoneNo
            binding?.entityPhoneNumberHeader?.visibility = View.GONE
        }

        if (searchResult?.permanentlyClosed != null) {
            binding?.entityAlwaysClosed?.text = getString(R.string.perm_closed)
            binding?.entityAlwaysClosed?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_c1))
        } else {
            binding?.entityAlwaysClosed?.text = getString(R.string.open)
            binding?.entityAlwaysClosed?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_c1))
        }

        CategoryAndFiltersUtil.setStarsViewBasedOnRating(entity_root, searchResult?.ratingLevel!!, requireContext())
    }

    fun updateStartsVisibility(slideOffset: Float) {
        if (slideOffset == 0f) {
            binding?.entityStars?.visibility = View.GONE
        } else {
            binding?.entityStars?.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(searchResult: SearchResult?) =
            FoodDetailsFragment().apply {
                this.searchResult = searchResult
            }
    }
}

