package com.example.chvui.siamservicebasic.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.example.chvui.siamservicebasic.R;

/**
 * Created by chvui on 09.10.2017.
 */

public final class NavigatorUtils {

    private NavigatorUtils() {

    }

    public static void navigateToCallActivity(Context context, String phone) {
        if (context != null) {
            Intent intentToLaunch = new Intent(Intent.ACTION_CALL);
            intentToLaunch.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(intentToLaunch);
        }
    }

    public static void navigateToMapActivity(Context context, String latitude, String longitude) {
        if (context != null) {
            Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + latitude + "," + longitude);
            Intent intentToLaunch = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            context.startActivity(intentToLaunch);
        }
    }

    public static void navigateToWebActivity(Context context, String web) {
        if (context != null) {
            Intent intentToLaunch = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
            context.startActivity(intentToLaunch);
        }
    }

    public static void navigateToSendMailActivity(Context context, String mail) {
        if (context != null) {
            Intent intentToLaunch = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto",mail, null));
            intentToLaunch.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.mail_header));
            context.startActivity(intentToLaunch);
        }
    }
}
