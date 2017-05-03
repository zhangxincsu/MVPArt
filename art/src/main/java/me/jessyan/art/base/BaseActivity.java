package me.jessyan.art.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import me.jessyan.art.base.delegate.IActivity;
import me.jessyan.art.mvp.IPresenter;

import static me.jessyan.art.base.delegate.ActivityDelegate.LAYOUT_FRAMELAYOUT;
import static me.jessyan.art.base.delegate.ActivityDelegate.LAYOUT_LINEARLAYOUT;
import static me.jessyan.art.base.delegate.ActivityDelegate.LAYOUT_RELATIVELAYOUT;

/**
 * 因为java只能单继承,所以如果有需要继承特定Activity的三方库,那你就需要自己自定义Activity
 * 继承于这个特定的Activity,然后按照将BaseActivity的格式,复制过去记住一定要实现{@link IActivity}
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity<P> {
    protected final String TAG = this.getClass().getSimpleName();
    protected P mPresenter;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }
        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }
        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }


    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null) mPresenter = obtainPresenter();
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link com.jess.arms.base.BaseFragment} 的Fragment将不起任何作用
     * @return
     */
    @Override
    public boolean useFragment() {
        return true;
    }

}
