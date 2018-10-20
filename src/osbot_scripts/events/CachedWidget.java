package osbot_scripts.events;

import java.util.Optional;

import org.osbot.rs07.api.Widgets;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.ui.RS2Widget;

public class CachedWidget {

    private int rootID = -1, secondLevelID = -1, thirdLevelID = -1;
    private String[] widgetTexts;
    private Filter<RS2Widget> filter;

    public CachedWidget(final int rootID, final int secondLevelID){
        this.rootID = rootID;
        this.secondLevelID = secondLevelID;
    }

    public CachedWidget(final int rootID, final int secondLevelID, final int thirdLevelID){
        this.rootID = rootID;
        this.secondLevelID = secondLevelID;
        this.thirdLevelID = thirdLevelID;
    }

    public CachedWidget(final int rootID, final String... widgetTexts) {
        this.rootID = rootID;
        this.widgetTexts = widgetTexts;
    }

    public CachedWidget(final String... widgetTexts){
        this.widgetTexts = widgetTexts;
    }

    public CachedWidget(final int rootID, final Filter<RS2Widget> filter) {
        this.rootID = rootID;
        this.filter = filter;
    }

    public CachedWidget(final Filter<RS2Widget> filter) {
        this.filter = filter;
    }

    public Optional<RS2Widget> getParent(final Widgets widgets) {
        return get(widgets).map(widget -> {
            if (widget.isSecondLevel()) {
                return widget;
            }
            return widgets.get(widget.getRootId(), widget.getSecondLevelId());
        });
    }

    public Optional<RS2Widget> get(final Widgets widgets){
        if(rootID != -1 && secondLevelID != -1 && thirdLevelID != -1) {
            return Optional.ofNullable(widgets.get(rootID, secondLevelID, thirdLevelID));
        } else if(rootID != -1 && secondLevelID != -1) {
            return getSecondLevelWidget(widgets);
        } else if (widgetTexts != null) {
            return getWidgetWithText(widgets);
        } else {
            return getWidgetUsingFilter(widgets);
        }
    }

    private Optional<RS2Widget> getSecondLevelWidget(final Widgets widgets){
        RS2Widget rs2Widget = widgets.get(rootID, secondLevelID);
        if(rs2Widget != null && rs2Widget.isThirdLevel()){
            thirdLevelID = rs2Widget.getThirdLevelId();
        }
        return Optional.ofNullable(rs2Widget);
    }

    private Optional<RS2Widget> getWidgetWithText(final Widgets widgets){
        RS2Widget rs2Widget;
        if (rootID != -1) {
            rs2Widget = widgets.getWidgetContainingText(rootID, widgetTexts);
        } else {
            rs2Widget = widgets.getWidgetContainingText(widgetTexts);
        }
        setWidgetIDs(rs2Widget);
        return Optional.ofNullable(rs2Widget);
    }

    private Optional<RS2Widget> getWidgetUsingFilter(final Widgets widgets) {
        RS2Widget rs2Widget;
        if (rootID != -1) {
            rs2Widget = widgets.singleFilter(rootID, filter);
        } else {
            rs2Widget = widgets.singleFilter(widgets.getAll(), filter);
        }
        setWidgetIDs(rs2Widget);
        return Optional.ofNullable(rs2Widget);
    }

    private void setWidgetIDs(final RS2Widget rs2Widget) {
        if (rs2Widget == null) {
            return;
        }
        rootID = rs2Widget.getRootId();
        secondLevelID = rs2Widget.getSecondLevelId();
        if (rs2Widget.isThirdLevel()) {
            thirdLevelID = rs2Widget.getThirdLevelId();
        }
    }

    @Override
    public String toString() {
        return rootID + ", " + secondLevelID + ", " + thirdLevelID;
    }
}