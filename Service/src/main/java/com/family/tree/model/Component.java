package com.family.tree.model;

import java.util.List;

public class Component<T> 
{
	private List<T> data;
	private long count;
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Component [count=" + count + "]";
	}

}
