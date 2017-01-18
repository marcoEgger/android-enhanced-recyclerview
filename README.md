# android-enhanced-recyclerview

[ ![Download](https://api.bintray.com/packages/marcoegger/android/enhanced-recyclerview/images/download.svg) ](https://bintray.com/marcoegger/android/enhanced-recyclerview/_latestVersion)

This library provides an enhanced RecyclerView and other useful tools for lists, e.g. an RecyclerView adapter for
endless scrolling. It supports the 3 standard layout manager (```LinearLayoutManager```, ```GridLayoutManager```
and ```StaggeredGridLayoutManager```).

## Dependency
```gradle
compile 'de.marco-egger:enhanced-recyclerview:1.1.0'
```

## Usage EnhancedRecyclerView

### Layout file
```xml
    <de.marco_egger.enhanced_recyclerview.EnhancedRecyclerView
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

### Activity/fragment setup
```java
// Get the view reference
EnhancedRecyclerView list = (EnhancedRecyclerView) findViewById(R.id.list);
// Also get a reference of the empty and loading view

// Then pass this reference to the recycler view
list.setEmptyView(emptyView);
list.setLoadingView(loadingView);
```

### Showing the loading view
```java
list.setLoading(true);
```

### Showing the empty view
Nothing has to be done, it will be shown when no adapter is set or when the item count is 0.

### Example
Have a look at the full
[example](https://github.com/marcoEgger/android-enhanced-recyclerview/tree/master/example-enhanced-recyclerview)
of the ```EnhancedRecyclerView``` in conjunction with the ```EnhancedRecyclerViewAdapter``` and
```EnhancedViewHolder```. Note that the ```EnhancedRecyclerView``` can be used **without** any other component of this library!

## Usage EnhancedRecyclerViewAdapter
**Note:** For now the EnhancedRecyclerViewAdapter only supports a single ViewHolder type, so if you want to have a list
with different views for different items you have to use the standard RecyclerView.Adapter.

Let your adapter extend the EnhancedRecyclerViewAdapter:
```java
public class TasksAdapter extends EnhancedRecyclerViewAdapter<MyDataClass, MyViewHolder>
```
**Note:** that ```MyViewHolder``` has to extend the ```EnhancedViewHolder``` class provided by this library.

### Example
Have a look at the full
[example](https://github.com/marcoEgger/android-enhanced-recyclerview/tree/master/example-enhanced-recyclerview)
of the EnhancedRecyclerView in conjunction with the EnhancedRecyclerViewAdapter and EnhancedViewHolder.

## Usage EndlessRecyclerViewAdapter

### When you create your standard adapter
```java
RecyclerView.Adapter myAdapter = new MyAdapter();
EndlessRecyclerViewAdapter endlessAdapter = new EndlessRecyclerViewAdapter(myAdapter, myLayourResId
        new EndlessRecyclerViewAdapter.EndlessScrollEventListener() {
            @Override
            public void onLoadMoreRequested() {
                // Load new data, e.g. send network request to fetch next items
            }
        });
```
With ```myLayoutResId``` you can specify a view that should be inflated to indicate the user that new data is loaded.
This can be a ```ProgressBar``` for example.

### New data received
Simply add your data to your normal adapter and then call ```endlessAdapter.onDataReady()```.

### Stop loading new data
Call ```endlessAdapter.stopLoading()``` to stop loading new data.

### Restart loading new data
Call ```endlessAdapter.restartLoading()``` to restart loading of new data.

### Example
Will come soon.

## License

    MIT License

    Copyright (c) 2017 Marco Egger

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
