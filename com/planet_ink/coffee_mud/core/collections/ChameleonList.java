package com.planet_ink.coffee_mud.core.collections;
import java.lang.ref.WeakReference;
import java.util.*;

/*
   Copyright 2011-2023 Bo Zimmerman

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
public class ChameleonList<K> implements List<K>, SizedIterable<K>
{
	private volatile List<K> 		list;
	private volatile Signaler<K>	signaler;

	public static abstract class Signaler<K>
	{
		protected WeakReference<List<K>> 	oldReferenceListRef;

		public Signaler(final List<K> referenceList)
		{
			oldReferenceListRef = new WeakReference<List<K>>(referenceList);
		}

		public abstract void rebuild(final ChameleonList<K> me);

		public abstract boolean isDeprecated();

		public final synchronized void possiblyChangeMe(final ChameleonList<K> me)
		{
			if(!isDeprecated())
				return;
			rebuild(me);
		}
	}

	public ChameleonList(final List<K> l, final Signaler<K> signaler)
	{
		list=l;
		this.signaler = signaler;
	}

	public void changeMeInto(final ChameleonList<K> fromList)
	{
		this.list=fromList.list;
		this.signaler=fromList.signaler;
	}

	public Signaler<K> getSignaler()
	{
		return signaler;
	}

	@Override
	public boolean add(final K arg0)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public void add(final int arg0, final K arg1)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public boolean addAll(final Collection<? extends K> arg0)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public boolean addAll(final int arg0, final Collection<? extends K> arg1)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public void clear()
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public boolean contains(final Object arg0)
	{
		signaler.possiblyChangeMe(this);
		return list.contains(arg0);
	}

	@Override
	public boolean containsAll(final Collection<?> arg0)
	{
		signaler.possiblyChangeMe(this);
		return list.containsAll(arg0);
	}

	@Override
	public K get(final int arg0)
	{
		signaler.possiblyChangeMe(this);
		return list.get(arg0);
	}

	@Override
	public int indexOf(final Object arg0)
	{
		signaler.possiblyChangeMe(this);
		return list.indexOf(arg0);
	}

	@Override
	public boolean isEmpty()
	{
		signaler.possiblyChangeMe(this);
		return list.isEmpty();
	}

	@Override
	public Iterator<K> iterator()
	{
		signaler.possiblyChangeMe(this);
		return new ReadOnlyIterator<K>(list.iterator());
	}

	@Override
	public int lastIndexOf(final Object arg0)
	{
		signaler.possiblyChangeMe(this);
		return list.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<K> listIterator()
	{
		signaler.possiblyChangeMe(this);
		return new ReadOnlyListIterator<K>(list.listIterator());
	}

	@Override
	public ListIterator<K> listIterator(final int arg0)
	{
		signaler.possiblyChangeMe(this);
		return new ReadOnlyListIterator<K>(list.listIterator(arg0));
	}

	@Override
	public boolean remove(final Object arg0)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public K remove(final int arg0)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public boolean removeAll(final Collection<?> arg0)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public boolean retainAll(final Collection<?> arg0)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public K set(final int arg0, final K arg1)
	{
		throw new java.lang.IllegalArgumentException();
	}

	@Override
	public int size()
	{
		signaler.possiblyChangeMe(this);
		return list.size();
	}

	@Override
	public List<K> subList(final int arg0, final int arg1)
	{
		signaler.possiblyChangeMe(this);
		return new ReadOnlyList<K>(list.subList(arg0,arg1));
	}

	@Override
	public Object[] toArray()
	{
		signaler.possiblyChangeMe(this);
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] arg0)
	{
		signaler.possiblyChangeMe(this);
		return list.toArray(arg0);
	}
}
