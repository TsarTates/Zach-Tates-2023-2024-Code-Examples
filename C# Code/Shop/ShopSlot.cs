using CSE5912.PenguinProductions.Items;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

namespace CSE5912.PenguinProductions
{
    public class ShopSlot : MonoBehaviour, IPointerEnterHandler, IPointerExitHandler
    {
        [SerializeField] private Image _background, _itemIcon;
        [SerializeField] private Sprite _emptySlotIcon;

        private readonly Color NORMAL_COLOR = new(0.55f, 0.55f, 0.55f, 1);
        private readonly Color SELECTED_COLOR = new(1, 1, 1, 1);

        private Item _nullItem;
        private bool _mouseOnSlot;

        public void Init(Item nullItem) 
        {
            _nullItem = nullItem;
            _mouseOnSlot = false;

            SetItem(nullItem);
            _itemIcon.type = Image.Type.Simple;
            _itemIcon.preserveAspect = true;
            _background.color = NORMAL_COLOR;
        }

        public void SetItem(Item item)
        {
            _itemIcon.sprite = item.icon;

            if (item == _nullItem && _emptySlotIcon != null) _itemIcon.sprite = _emptySlotIcon;
            _itemIcon.gameObject.SetActive(item != _nullItem || _emptySlotIcon != null);
        }

        public void OnPointerEnter(PointerEventData eventData)
        {
            _mouseOnSlot = true;
            _background.color = SELECTED_COLOR;
        }

        public void OnPointerExit(PointerEventData eventData)
        {
            _mouseOnSlot = false;
            _background.color = NORMAL_COLOR;
        }

        public bool WasClicked() 
        {
            return _mouseOnSlot;
        }
    }
}
