package org.uees.model.emilio;

import java.util.ArrayList;
import java.util.List;

public class EmilioTaskQueue {
    
    private List<EmilioTask> heap;
    private EmilioTaskComparator comparator;
    
    public EmilioTaskQueue() {
        this.heap = new ArrayList<>();
        this.comparator = EmilioTaskComparator.byPriorityDesc();
    }
    
    public EmilioTaskQueue(EmilioTaskComparator comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }
    
    public void enqueue(EmilioTask task) {
        heap.add(task);
        heapifyUp(heap.size() - 1);
    }
    
    public EmilioTask dequeue() {
        if (isEmpty()) return null;
        
        EmilioTask root = heap.get(0);
        EmilioTask lastElement = heap.remove(heap.size() - 1);
        
        if (!isEmpty()) {
            heap.set(0, lastElement);
            heapifyDown(0);
        }
        
        return root;
    }
    
    public EmilioTask peek() {
        return isEmpty() ? null : heap.get(0);
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public int size() {
        return heap.size();
    }
    
    public boolean contains(EmilioTask task) {
        return heap.contains(task);
    }
    
    public boolean remove(EmilioTask task) {
        int index = heap.indexOf(task);
        if (index == -1) return false;
        
        EmilioTask lastElement = heap.remove(heap.size() - 1);
        
        if (index < heap.size()) {
            heap.set(index, lastElement);
            
            if (index > 0 && comparator.compare(heap.get(index), heap.get((index - 1) / 2)) > 0) {
                heapifyUp(index);
            } else {
                heapifyDown(index);
            }
        }
        
        return true;
    }
    
    public List<EmilioTask> getAllSorted() {
        List<EmilioTask> result = new ArrayList<>();
        EmilioTaskQueue tempQueue = new EmilioTaskQueue(comparator);
        
        for (EmilioTask task : heap) {
            tempQueue.enqueue(task);
        }
        
        while (!tempQueue.isEmpty()) {
            result.add(tempQueue.dequeue());
        }
        
        return result;
    }
    
    public List<EmilioTask> getFilteredSorted(EmilioTask.Priority priority, String searchText, EmilioTask.TaskStatus status) {
        List<EmilioTask> filtered = new ArrayList<>();
        
        for (EmilioTask task : heap) {
            if (task.matchesPriority(priority) && 
                task.matchesSearchText(searchText) && 
                task.matchesStatus(status)) {
                filtered.add(task);
            }
        }
        
        filtered.sort(comparator);
        return filtered;
    }
    
    public List<EmilioTask> getPendingTasksSorted() {
        return getFilteredSorted(null, null, EmilioTask.TaskStatus.PENDING);
    }
    
    public void clear() {
        heap.clear();
    }
    
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parentIndex)) <= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }
    
    private void heapifyDown(int index) {
        while (true) {
            int largest = index;
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            
            if (leftChild < heap.size() && 
                comparator.compare(heap.get(leftChild), heap.get(largest)) > 0) {
                largest = leftChild;
            }
            
            if (rightChild < heap.size() && 
                comparator.compare(heap.get(rightChild), heap.get(largest)) > 0) {
                largest = rightChild;
            }
            
            if (largest == index) break;
            
            swap(index, largest);
            index = largest;
        }
    }
    
    private void swap(int i, int j) {
        EmilioTask temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
