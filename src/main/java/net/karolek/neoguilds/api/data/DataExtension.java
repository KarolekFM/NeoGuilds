package net.karolek.neoguilds.api.data;

public interface DataExtension<T extends DataExtension> {

    void setData(Class<? extends Data<T>> clazz, AbstractData<T> data);

    <U extends Data<T>> U getData(Class<U> clazz);

}
