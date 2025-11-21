package edu.university.registration.repository;

import edu.university.registration.model.course.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemorySectionRepository implements Repository<Section, String> {

    private Map<String, Section> data = new HashMap<>();

    @Override
    public Section save(Section section) {
        if (section == null) return null;
        data.put(section.getId(), section);
        return section;
    }

    @Override
    public Section findById(String id) {
        if (id == null) return null;
        return data.get(id);
    }

    @Override
    public List<Section> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) return false;
        return data.remove(id) != null;
    }
}
