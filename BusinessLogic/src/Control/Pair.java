package Control;

import java.io.Serializable;

public class Pair<K, V> implements Serializable {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;
		private K first;
		private V second;

		public Pair(K first, V second) {
			this.first = first;
			this.second = second;
		}

		public void setFirst(K first) {
			this.first = first;
		}

		public K getFirst() {
			return this.first;
		}

		public void setSecond(V second) {
			this.second = second;
		}

		public V getSecond() {
			return this.second;
		}
	}
