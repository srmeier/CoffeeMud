package com.planet_ink.coffee_mud.core.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/*
   Copyright 2015-2023 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
public class SafeChildSortedMap<K, V> implements SortedMap<K, V>
{
	private final SortedMap<K, V>	map;
	private final SafeCollectionHost host;

	public SafeChildSortedMap(final SortedMap<K, V> s, final SafeCollectionHost host)
	{
		this.map = s;
		this.host = host;
	}

	@Override
	public Comparator<? super K> comparator()
	{
		return map.comparator();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return new SafeChildSet<java.util.Map.Entry<K, V>>(map.entrySet(), host);
	}

	@Override
	public K firstKey()
	{
		return map.firstKey();
	}

	@Override
	public SortedMap<K, V> headMap(final K toKey)
	{
		return new SafeChildSortedMap<K, V>(map.headMap(toKey), host);
	}

	@Override
	public Set<K> keySet()
	{
		return new SafeChildSet<K>(map.keySet(), host);
	}

	@Override
	public K lastKey()
	{
		return map.lastKey();
	}

	@Override
	public SortedMap<K, V> subMap(final K fromKey, final K toKey)
	{
		return new SafeChildSortedMap<K, V>(map.subMap(fromKey, toKey), host);
	}

	@Override
	public SortedMap<K, V> tailMap(final K fromKey)
	{
		return new SafeChildSortedMap<K, V>(map.tailMap(fromKey), host);
	}

	@Override
	public Collection<V> values()
	{
		return new SafeChildCollection<V>(map.values(), host);
	}

	@Override
	public void clear()
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public boolean containsKey(final Object key)
	{
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value)
	{
		return map.containsValue(value);
	}

	@Override
	public V get(final Object key)
	{
		return map.get(key);
	}

	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Override
	public V put(final K key, final V value)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public V remove(final Object key)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public int size()
	{
		return map.size();
	}

}
