## 前言
今天学习了**基于JDK1.8**的HashMap的源码，主要从如下几个方面来阐述，HashMap的数据结构，HashMap如何支持动态扩容，HashMap的散列函数是如何实现的，并且如何防止散列冲突，最后就是对HashMap的常用方法的源码解析。
## 目录
 1. HashMap的数据结构
 2. HashMap的散列函数
 3. 散列冲突的处理
 2. HashMap的扩容机制
 5. put 方法的源码解析
 6. get 方法和remove的源码解析
 
## 基本的全局常量
1. 默认初始化的容器大小16：
```java
 static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;  // aka 16  1 左移4位
```
2. 最大的数据容量2的30次方。也就是说最多存放2的30次方个数据

```java
static final int MAXIMUM_CAPACITY = 1 << 30;
```
3. 默认的加载因子 0.75f

```java
 static final float DEFAULT_LOAD_FACTOR = 0.75f;
```
PS: 散列表的加载因子=填入表中的元素个数/散列表的长度
加载因子越大，说明空闲位置越小，冲突越多，散列表的性能会下降。
4. 默认的链表转红黑树的链表长度

```java
 static final int TREEIFY_THRESHOLD = 8;
 
```
5. 默认的红黑树转链表的红黑树节点个数

```java
 static final int UNTREEIFY_THRESHOLD = 6;
```
6. 最小的链表树形化的table的大小。

```java
    static final int MIN_TREEIFY_CAPACITY = 64;
```
## HashMap的数据结构（基于JDK1.8）
HashMap的数据结构是散列表+链表+红黑树，其中散列表是其基本的数据结构（散列表使用的是桶数组，其实就是一个容量为N的普通数组A[0..N-1]。只不过，在这里我们要将每个单元都想象成一个"桶"（Bucket），每个桶单元里都可以存放一个条目。）。链表是用来存储散列值相同的结点的，当链表的默认长度大于8时链表就可能会转化成红黑树。
其数据结构图如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191221162220884.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQ1MzQ4MDg=,size_16,color_FFFFFF,t_70)
 从源码可知，HashMap类中有个非常重要的字段`Node<K,V>[] table`，即哈希桶数组，其实本质上就是一个数组。而Node是HashMap的一个内部类 ，实现了Map.Entry<K,V>接口，本质上就是一个键值对。
```java
static class Node<K,V> implements Map.Entry<K,V> {
        //用来定位数组索引位置（hash值）
		final int hash;
		//hash表的键
        final K key;
		//存储的值
        V value;
		//链表的下一个结点
        Node<K,V> next;
    }
```
## HashMap的散列函数
散列表中，我们需要一个函数，将任意键key转换为介于0与N-1之间的整数，这个函数就是散列函数（又称哈希函数），散列函数应该要满足如下三点基本要求：
1. 散列函数计算得到的散列值必须是一个非负整数（因为数组的下标不可能是负数）
2. 如果key1=key2, 那么hash(key1)=hash(key2)。
3. 如果key1=/=key2, 那么hash(key1)=/=hash(key2)。
在HashMap中散列函数的实现如下：
```java
 static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```
如上代码我们可以看出hashMap的散列函数是通过调用key的hashCode()方法得到其hashCode值（该方法适用于所有Java对象）。然后再通过hashCode值的高16位异或低16位（其中h >>> 16表示在二进制中将h右移16位）来得到hash值。
最后通过 `(n - 1) & hash;`（n-1对hash值做按位与运算，也就是求模运算） 得到该键值对的存储位置  。
下面举例说明，n为table的长度
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191221162403784.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQ1MzQ4MDg=,size_16,color_FFFFFF,t_70)
## 散列冲突的处理
当两个key定位到相同的位置是，就会发生散列冲突，散列函数计算结果越分数均匀，散列冲突的概率就会越小，map存储的效率就会越高。在HashMap中是通过链表法来处理,即位置相同的结点会存储到同位置上的链表上。具体的代码实现如下：
```java
//遍历链表
  for (int binCount = 0; ; ++binCount) {
  					//当p.next （后继指针）为null时，则设置node结点
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                    }
					//如果键和值已经存在则返回
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
```
如上代码所示：散列冲突之后
1. 首先遍历链表，在循环中，当p.next （后继指针）为null时，则设置node结点。最新冲突的元素在最后面
2. 如果键和值已经存在则直接返回已经存在的数据。
## HasMap的扩容机制
如果哈希桶数组很大，即使较差的散列函数也会比较分散，如果哈希桶数组很小，即使再好的散列函数，也会出现较多的散列冲突。所以，我们需要权衡时间成本和空间成本上权衡。其实就是根据实际情况确定哈希桶数组大小。并在此基础上设计较好的散列函数，HashMap就是通过良好的散列函数加扩容机制来控制map使得Hash碰撞较小。介绍扩容机制之前，我们需要知道几个重要的属性

```java
  int threshold;  //map所能容纳的键值对的极限
  final float loadFactor;   //装载因子
  int modCount;  //记录HashMap被结构修改的次数，用于fast-fail
  int size;  //map中包含的key-value的个数
```
HashMap的构造器主要是给这几个属性设值。	正如前面说到的HashMap默认的容器大小（capacity）是16，默认的转载因子（loadFactor）是0.75，而  threshold=`loadFactor*capacity`,也就是说转载因子越大，map所能容纳的键值对就越多。  当HashMap中元素的个数超过threshold就会启动扩容，每次扩容都会扩容到原来的两倍大小。默认的装载因子0.75是对空间和效率的一种平衡选择，建议大家不要修改。而size 表示HashMap中实际存在的键值对数量，modCount字段主要是用来记录HashMap内部结构发生变化的次数，主要用于迭代快速失败。例如put新键值对，但是对某个key对应的value值覆盖不属于结构变化。
其扩容主要分为如下两步：
1. 创建一个新的两倍于原容量的数组。
2. 循环将原数组中的数据移到新数组中。
具体代码如下：

```java
 final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) { //不是初始化，走扩容流程
            //超过最大值就不在扩容
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            //扩容后的容量是原来的2倍，左移一位就可以将数据翻倍
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; //向左位移一位，达到原阀值的2倍。
        }
        else if (oldThr > 0) // 初始化容量，容量大小是threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            //容量，阀值指定初值
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        //计算新的resize上限
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        //建立新容量数组
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            //遍历原来的数组，把所有的元素都转移到新数组上去
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null; //原数组置空，以便GC
                    if (e.next == null)  //原数组该位置无冲突，正常存放
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)  // 原数组在这个位置上是一个红黑树
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else {// 原数组该位置冲突，但是还未达到树形化阈值，因此还是链表结构
                        // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            // 循环链表转移至新数组
                            next = e.next;
                            //原索引
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            //原索引+oldCap
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        //原索引放在bucket里
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        //原索引+oldCap放在bucket里
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```
下面举个例子说明下扩容过程。假设了我们的hash算法就是简单的用key mod 一下表的大小（也就是数组的长度）。其中的哈希桶数组table的size=2， 所以key = 3、7、5，put顺序依次为 3、7、5。在mod 2以后都冲突在table[1]这里了。这里假设负载因子 loadFactor=1，即当键值对的实际大小size 大于 table的实际大小时进行扩容。接下来的三个步骤是哈希桶数组 resize成4，然后所有的Node重新rehash的过程。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191225100558973.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQ1MzQ4MDg=,size_16,color_FFFFFF,t_70)
经过观测可以发现，我们使用的是2次幂的扩展（指长度扩为原来的两倍），所以元素的位置要么在原来位置，要么是在原来位置再移动2次幂的位置，看下图就可以明白这句话的意思，n为table的长度，图（a）表示扩容前key1和key2确定的索引位置示例，图（b）表示扩容后key1和key2两种key确定索引位置的示例，其中hash1是key1对应的哈希与高位运算结果。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191221162500837.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQ1MzQ4MDg=,size_16,color_FFFFFF,t_70)
元素在重新计算hash之后,因为n变为2倍，那么n-1的mask范围在高位多1bit(红色) 因此新的index就会发生这样的变化：
                   resize                00101=5                           原位置
0101=5     ————>      
				16==>`2*16`        10101=21=5+16              原位置+oldCap
因此，我们在扩充HashMap的时候，不在需要像JDK1.7实现的那样重新计算hash。只需要看原来的hash值新增的那个bit是1还是0就好了，是0的话索引没变，是1的话索引就变成 原索引+oldCap,可以看看下图为16扩充为32的resize示意图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191221162457799.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQ1MzQ4MDg=,size_16,color_FFFFFF,t_70)
##  put 方法的源码解析
put方法是操作HashMap是最常用的方法，它的就用就是将数据放到HashMap中，其流程图如下所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/201912211625528.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQ1MzQ4MDg=,size_16,color_FFFFFF,t_70)
 如上所示主要有一下几个步骤：
 1. 首先判断散列表是否为空，为空的话则先初始化数组。
 2. 根据键值key计算hash值并得到插入的数组索引
 2. 如果索引值没有被占用则直接插入键值对
 3. 如果索引值被占用则判断key是否存在，存在的话则直接覆盖value，不存在的话则判断当前节点是否是TreeNode。如果是的话则走红黑树直接插入键值对。
 4. 插入完元素之后，则判断当前的数据容量是否大于传入的数组大小，如果大于的话则进行扩容。
 因为put方法只是调用putVal方法，所以，我们只需要分析putVal方法即可。
```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        //如果table还没未被初始化，则直接进行初始化，一般在HashMap被定义后，首次调用put方法时被触发
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)
            //如果计算得到的位置没有被占用，则直接存放。
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            //判断是否是同一个key
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;  //标记冲突的头结点e
            //是否已经树形化过
            else if (p instanceof TreeNode)
                //已经转化为红黑树，将节点插入红黑树，红黑树的插入涉及到左旋右旋
                // 以及颜色变换等操作，以满足红黑树的几大特性。
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {//哈希冲突，链表法处理初步冲突
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //链表深度达到树形化阀值，触发树形化流程
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //相同的key,用新的value替换原来的value,并返回原来的value
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        //modCount自增1，用于fast-fail
        ++modCount;
        //达到扩容阀值，进行扩容
        if (++size > threshold)
            resize();
        //put操作evict为true,仅当构造方法内evict为false。
        afterNodeInsertion(evict);
        return null;
    }

```
其中modCount的作用的记录设置的新的key的次数，用于fast-fail。
## get方法的源码解析
get方法是根据传入的key,从HashMap中取出相应的value。如果找不到则返回null，能找到的话则返回找到的value。
流程图如下：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191221162601806.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTQ1MzQ4MDg=,size_16,color_FFFFFF,t_70)
 如上流程图：主要的流程说明是：
 1. 首先判断传入的key,计算得到的数组下标是否为空，为空的话直接返回null。
 2. 不为空的话，则查找位置上的第一个元素是否符合，如果符合的话则返回第一个元素的node
 3. 如果不符合的话，则接着判断结点是否是TreeNode，是的话则从红黑树中搜索对应的key。
 4.如果不是话则遍历链表，知道找到需要的传入的key，最后返回node。
源代码如下：
get 代码只是定义了一个Node，然后调用了getNode方法

```java
 public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
```
getNode的源码如下所示：

```java
final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        //根据传入hash值做按位与运算（取模运算）在哈希桶中的位置是否为空，如果为空则返回null
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            //总是检查位置上的第一个元素，如果第一个元素符合则直接返回，
            // 不用管这个桶的位置上是链表还是红黑树
            if (first.hash == hash &&
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            //执行到这，表示这个位置上至少是一个链表了。
            if ((e = first.next) != null) {
                //判断这个位置是否已经树形化过
                if (first instanceof TreeNode)
                    //从红黑树中搜索对应的key,时间复杂度O(logn)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                //还未树形化，则遍历链表，直到找到对应的key。时间复杂度是0(n)
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

```
## remove方法的源码解析
remove 方法可以移除指定的key的元素。与get方法类似，其方法内部也是调用了一个removeNode主体方法来处理元素的移除，removeNode方法的源代码如下：
```java
final Node<K,V> removeNode(int hash, Object key, Object value,
                               boolean matchValue, boolean movable) {
        Node<K,V>[] tab; Node<K,V> p; int n, index;
        //数组不为空，找到要移除的key对应的node
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (p = tab[index = (n - 1) & hash]) != null) {
            Node<K,V> node = null, e; K k; V v;
            //哈希值相等，且与key为同一对象，记录结点node
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                node = p;
            //next不为空，证明key与其他对象发生哈希冲突
            else if ((e = p.next) != null) {
                //链表已经树形化过
                if (p instanceof TreeNode)
                    node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
                else {  //仅为链表，未树形化
                    //遍历找到要移除的结点node
                    do {
                        if (e.hash == hash &&
                            ((k = e.key) == key ||
                             (key != null && key.equals(k)))) {
                            node = e;
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null);
                }
            }
            //确定要移除的node，开始根据不同的数据结构移除的node结点
            if (node != null && (!matchValue || (v = node.value) == value ||
                                 (value != null && value.equals(v)))) {
                if (node instanceof TreeNode) //红黑树
                    // 红黑树移除节点相对要复杂一些，因为删除一个节点很有可能会改变红黑树的结构，
                    // 因此需要做一些左右旋以及重新着色来使得整棵树满足一棵红黑树
                    ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
                else if (node == p)  //不冲突移除
                    tab[index] = node.next;
                else //链表移除
                    p.next = node.next;
                ++modCount; //modCount自增，记录修改次数
                --size; //size做相应减少
                afterNodeRemoval(node);
                return node;  //返回移除结点node
            }
        }
        return null;
    }

```

## 总结
1. HashMap 扩容操作比较耗时，所以，如果事先知道HashMap的大小的话，最好指定大小。
2. HasMap所以操作都没有加锁，所以其是线程不安全的容器，在多线程环境下，请使用并发容器concurrentHashMap。
## 参考
JDK1.8的源码
https://www.jianshu.com/p/992cc861832a
https://tech.meituan.com/2016/06/24/java-hashmap.html

