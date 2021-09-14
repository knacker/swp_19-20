/*
 * holds the data, where possible empty fields are already existing fields with cards.
 */
package de.btu.swp.carcassonne.data.card;

public class PossibleField {

	private final Field field;
	private final Rotation rotation;
	private final FieldPriority prio;
	
	public PossibleField(Field field, Rotation rotation, FieldPriority prio) {
		this.field = field;
		this.rotation = rotation;
		this.prio = prio;
	}
	
	public Field getField() {
		return field;
	}
	
	public Rotation getRotation() {
		return rotation;
	}
	
	public FieldPriority getPriority() {
		return prio;
	}

}
