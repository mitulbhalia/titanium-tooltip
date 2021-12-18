/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2018 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.tooltip;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollFunction;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.titanium.proxy.TiViewProxy;

import android.app.Activity;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;

import com.github.florent37.viewtooltip.ViewTooltip;

@Kroll.module(name="TitaniumTooltip", id="ti.tooltip")
public class TitaniumTooltipModule extends KrollModule {

	@Kroll.constant public static final int TOOLTIP_DIRECTION_UP = 0;
	@Kroll.constant public static final int TOOLTIP_DIRECTION_DOWN = 1;
	@Kroll.constant public static final int TOOLTIP_DIRECTION_LEFT = 2;
	@Kroll.constant public static final int TOOLTIP_DIRECTION_RIGHT = 3;

	private ViewTooltip activeTooltip = null;
	
	private ViewTooltip.Position getDirection(int direction) {
		switch (direction) {
			case 0:
				return ViewTooltip.Position.TOP;
				
			case 1:
				return ViewTooltip.Position.BOTTOM;
				
			case 2:
				return ViewTooltip.Position.LEFT;
				
			case 3:
				return ViewTooltip.Position.RIGHT;
				
			default:
				return ViewTooltip.Position.TOP;
		}
	}

	@Kroll.method
	public void show(final KrollDict params) {
		TiViewProxy sourceView = (TiViewProxy) params.get(Defaults.PARAMS_SOURCE_VIEW);
		
		String title = params.optString(Defaults.PARAMS_TITLE, "");
		int padding = params.optInt(Defaults.PARAMS_PADDING, Defaults.Values.PADDING);
		KrollFunction onClick = (KrollFunction) params.get("onClick");

		activeTooltip = ViewTooltip.on(TiApplication.getInstance().getCurrentActivity(), sourceView.getOrCreateView().getNativeView())
			.text(title)
			.clickToHide(true)
			.autoHide(false, 2000)
			.textColor( Utils.getColor(params, Defaults.PARAMS_TEXT_COLOR, Defaults.Values.TEXT_COLOR) )
			.withShadow(false)
			.color( Utils.getColor(params, Defaults.PARAMS_BACKGROUND_COLOR, Defaults.Values.BACKGROUND_COLOR) )
			.border(Color.LTGRAY, 1)
			.position( getDirection(params.optInt(Defaults.PARAMS_DIRECTION, Defaults.Values.DIRECTION)) )
			.padding(padding, padding, padding, padding)
			.arrowWidth( params.optInt(Defaults.PARAMS_ARROW_WIDTH, Defaults.Values.ARROW_WIDTH) )
			.arrowHeight( params.optInt(Defaults.PARAMS_ARROW_HEIGHT, Defaults.Values.ARROW_HEIGHT) )
			.distanceWithView( params.optInt(Defaults.PARAMS_ARROW_MARGIN, Defaults.Values.ARROW_MARGIN) )
			.onHide(view -> {
				if (onClick != null) {
					KrollDict event = new KrollDict();
					event.put("success", true);

					onClick.callAsync(getKrollObject(), event);
				}
				activeTooltip = null;
			});

		activeTooltip.show();
	}

	@Kroll.method
	public void hideActiveTooltip() {
		if (activeTooltip != null) {
			activeTooltip.close();
		}
	}
}
