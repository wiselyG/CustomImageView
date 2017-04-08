
### AnimationImageView

带侦动画功能的ImageView

xml中使用

```
<org.wisely.library.AnimationImageView
        android:id="@+id/ani_iv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/info_tv"
        app:aiv_animationlist="@drawable/wifi_animation" //drawable中定义的animation-set
        app:aiv_oneshot="false" //单次动画还是循环
        app:aiv_runonshow="false" //是否直接显示动画效果
        />
```

## ClipImageView

剪切ImageView中的图片

xml中使用

```
 <org.wisely.library.ClipImageView
        android:id="@+id/clip_iv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:civ_height="640"//截取的图片高度
        app:civ_src="@drawable/image_bak"//截取对象
        app:civ_width="800"//截取的图片宽度
        app:civ_padding_left=""//左边距
        app:civ_padding_top=""//上边距
        app:civ_padding_bottom=""//下边距
        app:civ_padding_right=""//右边距
        app:civ_clip_gravity="center"//截取中心点区域的图片
         />
```

`civ_src``civ_width``civ_height`这三个是必备参数，其它可选。
`civ_src`可以通过在代码中用方法设置

`civ_padding_left``civ_padding_right`同时存在，默认使用`civ_padding_left`

`civ_padding_top``civ_padding_bottom`同时存在，默认使用`civ_padding_top`

`civ_clip_gravity`存在时，忽略其它padding参数