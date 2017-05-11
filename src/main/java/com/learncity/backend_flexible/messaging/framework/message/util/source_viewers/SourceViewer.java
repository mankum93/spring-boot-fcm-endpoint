package com.learncity.backend_flexible.messaging.framework.message.util.source_viewers;

/**
 * Created by DJ on 5/3/2017.
 */
public interface SourceViewer<Source> {

    void setSource(Source source);
    Source viewSource();
}
