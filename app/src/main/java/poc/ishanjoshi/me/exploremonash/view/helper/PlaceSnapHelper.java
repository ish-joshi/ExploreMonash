package poc.ishanjoshi.me.exploremonash.view.helper;

import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

///////https://medium.com/over-engineering/detecting-snap-changes-with-androids-recyclerview-snaphelper-9e9f5e95c424



public class PlaceSnapHelper extends LinearSnapHelper {
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager lm, int velocityX, int velocityY) {
        View centerView = findSnapView(lm);
        if (centerView == null)
            return RecyclerView.NO_POSITION;

        int position = lm.getPosition(centerView);
        int targetPosition = -1;
        if (lm.canScrollHorizontally()) {
            if (velocityX < 0) {
                targetPosition = position - 1;
            } else {
                targetPosition = position + 1;
            }
        }

        if (lm.canScrollVertically()) {
            if (velocityY < 0) {
                targetPosition = position - 1;
            } else {
                targetPosition = position + 1;
            }
        }

        final int firstItem = 0;
        final int lastItem = lm.getItemCount() - 1;
        targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));

        return targetPosition;
    }


}
