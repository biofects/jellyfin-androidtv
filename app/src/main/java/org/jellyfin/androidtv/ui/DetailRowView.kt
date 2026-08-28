package org.jellyfin.androidtv.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import org.jellyfin.androidtv.R
import org.jellyfin.androidtv.databinding.ViewRowDetailsBinding
import org.jellyfin.androidtv.util.Utils

class DetailRowView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
	defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
	val binding = ViewRowDetailsBinding.inflate(LayoutInflater.from(context), this, true)

	/**
	 * Keeps track of the last selected button and reselect it when navigating back to the buttons row.
	 */
	private val buttonsHierarchyChangeListener = object : OnHierarchyChangeListener {

		private var lastFocusedButton: View? = null

		private val focusChangeListener = OnFocusChangeListener { view, hasFocus ->
			// Restore last focused button when navigating back to button row
			if (hasFocus && lastFocusedButton != null) {
				lastFocusedButton?.requestFocus()
				lastFocusedButton = null
			}
			view.post {
				// Store last focused button when navigating away from button row
				if (binding.fdButtonRow.focusedChild == null) {
					lastFocusedButton = view
				}
			}
		}

		override fun onChildViewAdded(parent: View?, child: View?) {
			child?.onFocusChangeListener = focusChangeListener
		}

		override fun onChildViewRemoved(parent: View?, child: View?) {
			child?.onFocusChangeListener = null
		}
	}

	init {
		binding.fdButtonRow.setOnHierarchyChangeListener(buttonsHierarchyChangeListener)
		binding.mainImage.clipToOutline = true

		val attributes = context.theme.obtainStyledAttributes(intArrayOf(R.attr.biofectsDetailsLayout))
		val useBiofectsLayout = attributes.getBoolean(0, false)
		attributes.recycle()

		if (useBiofectsLayout) applyBiofectsLayout()
	}

	private fun applyBiofectsLayout() {
		val constraintSet = ConstraintSet().apply { clone(binding.detailsContent) }
		val horizontalMargin = Utils.convertDpToPixel(context, 28)
		binding.fdSummaryText.setBackgroundResource(R.drawable.details_panel_background)

		constraintSet.setGuidelineBegin(R.id.guide_main_start, Utils.convertDpToPixel(context, 270))
		constraintSet.setGuidelineEnd(R.id.guide_main_end, Utils.convertDpToPixel(context, 50))

		constraintSet.clear(R.id.mainImageContainer)
		constraintSet.connect(R.id.mainImageContainer, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, horizontalMargin)
		constraintSet.connect(R.id.mainImageContainer, ConstraintSet.TOP, R.id.fdTitle, ConstraintSet.TOP)
		constraintSet.constrainWidth(R.id.mainImageContainer, Utils.convertDpToPixel(context, 220))
		constraintSet.constrainHeight(R.id.mainImageContainer, Utils.convertDpToPixel(context, 340))

		constraintSet.clear(R.id.fdSummaryText, ConstraintSet.BOTTOM)
		constraintSet.constrainHeight(R.id.fdSummaryText, ConstraintLayout.LayoutParams.WRAP_CONTENT)
		constraintSet.applyTo(binding.detailsContent)

		binding.infoTitle1.isVisible = false
		binding.infoValue1.isVisible = false
		binding.infoTitle2.isVisible = false
		binding.infoValue2.isVisible = false
		binding.infoTitle3.isVisible = false
		binding.infoValue3.isVisible = false
	}
}
