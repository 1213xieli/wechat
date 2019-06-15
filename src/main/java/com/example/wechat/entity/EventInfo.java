package com.example.wechat.entity;

import lombok.Data;

/**
 * EventInfo
 * time:2019/6/12
 * author:xieli
 */
@Data
public class EventInfo {
    private String id;//	string 或者 int 类型，事件的唯一标识。重复事件的不同实例应该都具有相同的ID。
    private String title;//	String，必选，事件名称。
    private String allDay;//	布尔型，true或false，可选项。事件是否发生在特定的时间。 该属性影响是否显示事件的时间。 此外，在议程视图中，确定是否显示在“全天”部分。
    private String start;//	事件开始日期/时间，必选。格式为ISO8601字符串或UNIX时间戳
    private String end;//	事件结束日期/时间，可选。格式为ISO8601字符串或UNIX时间戳
    private String url = "#";//	事件链接地址，字符串，可选。 当单击事件的时候会跳转到对应的url。
    private String string;//	string 或者 Array 类型，可选。 一个css类（或者一组），附加到事件的 DOM 元素上。
    private String editable;//	true或false，可选。只针对当前的单个事件，其他事件不受影响。
    private String startEditable;//	true或false，可选。覆盖当前事件的eventStartEditable选项
    private String durationEditable;//	true或false，可选。覆盖当前事件的eventDurationEditable选项
    private String resourceEditable;//	true或false，可选。 覆盖当前事件的 eventResourceEditable选项
    private String rendering;//	允许渲染事件，如背景色等。可以为空，也可以是"background", or"inverse-background"
    private String overlap;//	true或false，可选。覆盖当前事件的eventOverlap选项。如果设置为false，则阻止此事件被拖动/调整为其他事件。 还可以防止其他事件在此事件中被拖动/调整大小。
    private String constraint;//	事件id，"businessHours"，对象，可选。覆盖当前事件的eventConstraint选项。
    private String source;//	Event Source Object事件源
    private String color;//	和 eventColor 作用一样，设置事件的背景色和边框。
    private String backgroundColor;//	和 eventBackgroundColor 作用一样，设置事件的背景色。
    private String borderColor;//	和 eventBorderColor 作用一样，设置事件的边框。
    private String textColor;//	和 eventTextColor 作用一样，设置事件的文字颜色
    private String extendone;// 扩展字段
    private String eventType; // 事件类型
 }
