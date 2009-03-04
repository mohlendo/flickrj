package com.aetrion.flickr.machinetags;

public class Value {
    String value;
    int usage;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getUsage() {
        return usage;
    }

	public void setUsage(String predicates) {
        try {
            setUsage(Integer.parseInt(predicates));
        } catch (NumberFormatException e) {}
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }
}
