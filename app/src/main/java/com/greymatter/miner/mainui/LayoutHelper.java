package com.greymatter.miner.mainui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.game.manager.MainBaseComm;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.Scanner;
import com.greymatter.miner.game.objects.resources.CoalBlock;
import com.greymatter.miner.game.objects.resources.ResourceBlock;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.loaders.enums.Tag;

import java.util.ArrayList;

public class LayoutHelper {
    public static AlertDialog getScannerOnResourceFindDialog(Scanner scanner, ResourceBlock resourceBlock) {
        View view = LayoutHelper.loadLayout(R.layout.scanner_on_find_dialog);
        AlertDialog selectionDialog = LayoutHelper.loadDialog(view);
        TextView textView = view.findViewById(R.id.res_type_text_view);
        if(resourceBlock instanceof CoalBlock) {
            textView.setText("Coal Block");
        }

        selectionDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Mine Res", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToDrawContainer.add(GameObjectsContainer.get(ObjId.MINER_I));
                GameObjectsContainer.get(ObjId.MINER_I).asGameBuilding().snapTo(scanner.getLocation());
                dialog.dismiss();
            }
        });
        return selectionDialog;
    }

    public static AlertDialog loadDialog(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AppServices.getAppContext()).setView(v);
        AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        return dialog;
    }

    public static View loadLayout(int layout) {
        return AppServices.getAppContextAsActivity().getLayoutInflater().inflate(layout, null);
    }

    public static void showDialog(AlertDialog dialog) {
        dialog.show();
        dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public static void hide(int viewID) {
        View generalModeLL = AppServices.getAppContextAsActivity().findViewById(viewID);
        generalModeLL.setVisibility(View.INVISIBLE);
    }

    public static void show(int viewID) {
        View generalModeLL = AppServices.getAppContextAsActivity().findViewById(viewID);
        generalModeLL.setVisibility(View.VISIBLE);
    }
}
