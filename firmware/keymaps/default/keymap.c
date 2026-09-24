#include QMK_KEYBOARD_H

/* Define operational layers for the cognitive input system */
enum custom_layers {
    _BASE = 0,    // Normal text layer
    _COGNITIVE    // Triggered when holding the Mind Spacebar down
};

/* The physical key matrix matrix blueprint mapping */
const uint16_t PROGMEM keymaps[][MATRIX_ROWS][MATRIX_COLS] = {

    /* LAYER 0: THE RAW BASE KEYMAP */
    [_BASE] = LAYOUT_ortho_4x12(
        KC_ESC,  KC_Q,    KC_W,    KC_E,    KC_R,    KC_T,    KC_Y,    KC_U,    KC_I,    KC_O,    KC_P,    KC_BSPC,
        KC_TAB,  KC_A,    KC_S,    KC_D,    KC_F,    KC_G,    KC_H,    KC_J,    KC_K,    KC_L,    KC_SCLN, KC_ENT,
        KC_LSFT, KC_Z,    KC_X,    KC_C,    KC_V,    KC_B,    KC_N,    KC_M,    KC_COMM, KC_DOT,  KC_SLSH, KC_RSFT,
        KC_LCTL, KC_LGUI, KC_LALT, MO(_COGNITIVE), KC_SPC,  KC_SPC,  MO(_COGNITIVE), KC_RALT, KC_LEFT, KC_UP, KC_DOWN, KC_RGHT
    ),

    /* LAYER 1: THE COGNITIVE SYSTEM OVERLAY LAYER (Mind Spacebar Active) */
    [_COGNITIVE] = LAYOUT_ortho_4x12(
        _______, _______, _______, _______, _______, _______, _______, _______, _______, _______, _______, KC_DEL,
        _______, _______, _______, _______, _______, _______, KC_LEFT, KC_DOWN, KC_UP,   KC_RGHT, _______, _______,
        _______, _______, _______, KC_CALC, _______, _______, _______, _______, _______, _______, _______, _______,
        _______, _______, _______, _______, _______, _______, _______, _______, _______, _______, _______, _______
    )
};

/* Micro-second hardware macro parsing tracking matrix signals */
bool process_record_user(uint16_t keycode, keyrecord_t *record) {
    switch (keycode) {
        case KC_CALC:
            if (record->event.pressed) {
                // Low-level hardware hook passing signals straight to Android system framework
                SEND_STRING(SS_DOWN(X_LGUI) "c" SS_UP(X_LGUI)); 
            }
            return false;
        default:
            return true;
    }
}

