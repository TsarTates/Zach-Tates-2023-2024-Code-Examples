using System.Collections.Generic;
using UnityEngine;

namespace CSE5912.PenguinProductions
{
    public class KiwiReferences : MonsterReferences
    {
        [HideInInspector] public List<GameObject> itemPool;
        public AudioClip[] kiwiSFX;


        protected override void GetSpecificReferences()
        {
            itemPool = new();
        }
    }
}
