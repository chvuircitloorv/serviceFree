package com.example.chvui.siamservicebasic.ui.discovery;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.example.chvui.siamservicebasic.R;
import com.example.chvui.siamservicebasic.ui.base.BluetoothActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscoveryActivity extends BluetoothActivity implements DiscoveryView, BluetoothActivity.BluetoothDiscoveryListener {

    @BindView(R.id.list)
    ExpandableListView mListView;
    @BindView(R.id.scan)
    Button mScanButton;

    private String[] mGroups = new String[] {getString(R.string.paired), getString(R.string.new_device)};
    private String[] mPaired;
    private String[] mNews;

    // коллекция для групп
    ArrayList<Map<String, String>> groupData;

    // коллекция для элементов одной группы
    ArrayList<Map<String, String>> childDataItem;

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<Map<String, String>>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    // список атрибутов группы или элемента
    Map<String, String> m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        setUnBinder(ButterKnife.bind(this));

        setListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        setListener(null);
    }

    @Override
    protected void setUp() {
        // заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<>();
        for (String group : mGroups) {
            // заполняем список атрибутов для каждой группы
            m = new HashMap();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }

        // список атрибутов групп для чтения
        String groupFrom[] = new String[] {"groupName"};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};


        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<>();

        // создаем коллекцию элементов для первой группы
        childDataItem = new ArrayList<>();
        Set<BluetoothDevice> paired = getBoundedDevicies();
        mPaired = new String[paired.size()];
        int i = 0;
        for (BluetoothDevice element: paired) {
            mPaired[i] = element.getAddress();
            i++;
        }
        // заполняем список атрибутов для каждого элемента
        for (String phone : mPaired) {
            m = new HashMap();
            m.put("phoneName", phone); // название телефона
            childDataItem.add(m);
        }
        // добавляем в коллекцию коллекций
        childData.add(childDataItem);

        // создаем коллекцию элементов для второй группы
        if (mNews != null) {
            childDataItem = new ArrayList<>();
            for (String phone : mNews) {
                m = new HashMap();
                m.put("phoneName", phone);
                childDataItem.add(m);
            }
        }
        childData.add(childDataItem);

        // список атрибутов элементов для чтения
        String childFrom[] = new String[] {"phoneName"};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        mListView.setAdapter(adapter);
    }

    @Override
    public void showPairedDevices() {

    }

    @Override
    public void showNewFoundedDevices() {

    }

    @Override
    public void onActionChanged(String action) {
        Toast.makeText(this, action, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void discoveredDevices(Set<BluetoothDevice> devices) {
        hideLoading();

        mNews = devices.toArray(new String[devices.size()]);

    }

    @OnClick(R.id.scan)
    void onScanClick() {
        startDiscovering();
        showLoading();
    }
}
