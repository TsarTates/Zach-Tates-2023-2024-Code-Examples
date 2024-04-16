using CSE5912.PenguinProductions.Utility;
using System;
using UnityEngine;

namespace CSE5912.PenguinProductions
{
    public class KiwiStateMachine : MonsterStateMachine
    {
        private KiwiReferences kiwiReferences;

        private bool foundItem;
        private GameObject itemToEat;

        protected override void Start()
        {
            layerNumber = (int)LayerEnum.InteractableLayerMask;
            base.Start();

            itemToEat = new GameObject();
            kiwiReferences = GetComponent<KiwiReferences>();
            Rooms rooms = FindObjectOfType<Rooms>();
            RoomPosition homeBase = rooms.GetRandomRoomPosition(kiwiReferences.transform.position);

            //States
            var idle = new KiwiState_Idle(kiwiReferences, 3f);
            var attack = new KiwiState_Attack(kiwiReferences);
            var wander = new KiwiState_Wander(kiwiReferences, rooms);
            var spit = new KiwiState_SpitUp(kiwiReferences);
            var home = new KiwiState_GoHome(kiwiReferences, homeBase);
            var eat = new KiwiState_Eat(kiwiReferences, itemToEat);

            //Transitions
            At(idle, wander, () => idle.IsDone());
            At(wander, idle, () => wander.HasArrivedAtDestination());
            At(wander, eat, () => foundItem);
            At(eat, home, () => kiwiReferences.itemPool.Count == 3);
            At(home, spit, () => home.HasArrivedAtDestination());
            At(spit, idle, () => kiwiReferences.itemPool.Count == 0);

            //Start State
            stateMachine.SetState(idle);

            //Functions and Conditions
            void At(IState from, IState to, Func<bool> condition) => stateMachine.AddTransition(from, to, condition);
            void Any(IState to, Func<bool> condition) => stateMachine.AddAnyTransition(to, condition);
        }

        protected override void Look()
        {
            foundItem = false;

            for (int i = -180; i <= 180; i += 5)
            {
                for (int j = -50; j < 50; j += 5)
                {
                    Quaternion rotation = Quaternion.Euler(j, i, 0);
                    if (Physics.Raycast(transform.position, rotation * transform.forward, out hit, 5f, layerMask))
                    {
                        foundItem = true;
                        kiwiReferences.NavAgent.SetDestination(hit.transform.position);
                        itemToEat = hit.transform.gameObject;
                    }

                    Debug.DrawLine(transform.position, transform.position + rotation * transform.forward * 5f, Color.red);
                }
            }
        }
    }
}
