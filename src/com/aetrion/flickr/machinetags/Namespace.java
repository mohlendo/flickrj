package com.aetrion.flickr.machinetags;

public class Namespace {
    private int usage;
    private int predicates;
    private String value;
    
    public Namespace() {
    }

	public int getUsage() {
		return usage;
	}

    public void setUsage(String usage) {
        try {
            setUsage(Integer.parseInt(usage));
        } catch (NumberFormatException e) {}
    }

    public void setUsage(int usage) {
		this.usage = usage;
	}

    /**
     * Count of distinct predicates a namespace has.
     *
     * @return Number of predicates
     */
	public int getPredicates() {
		return predicates;
	}

    public void setPredicates(String predicates) {
        try {
            setPredicates(Integer.parseInt(predicates));
        } catch (NumberFormatException e) {}
    }

    public void setPredicates(int predicates) {
		this.predicates = predicates;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
