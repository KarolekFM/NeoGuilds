package net.karolek.neoguilds.api.data;

import lombok.Getter;

@Getter
public abstract class AbstractData<T extends DataExtension> implements Data<T> {

    protected final T t;

    public AbstractData(T t) {
        this.t = t;
        loadData();
    }

}
